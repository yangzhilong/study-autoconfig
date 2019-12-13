package com.longge.autoconfig.processor;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 动态修改server.host，使得springboot启动的时候绑定指定的网卡
 * 做法1：    可以动态化INTRANET_IP_PREFIX的IP参数从java启动参数或者properties里
 * 做法2：    可以在启动参数或者properties里指定优先的网卡名称列表来匹配
 * e.g： server.nic-name=lo1,enth2
 * @author roger yang
 *
 */
public class HostEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {
	private final String INTRANET_IP_PREFIX = "10.";
	private final String DEFAULT_PROPERTIES = "default.properties";
	private final String SERVER_ADDRESS = "server.address";
	
	@Override
	public int getOrder() {
		return 1;
	}
	
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		List<HostInfo> hosts = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
			while (enumeration.hasMoreElements()) {
				NetworkInterface net = enumeration.nextElement();
				
				String ipv4 = getIpv4(net);
				
				if(null == ipv4) {
					continue;
				}
				
				String shortName = net.getName();
				String displayName = net.getDisplayName();
				String mac = getMac(net);
				
				System.out.println("<<<<<<<<<<<<<<<--" + ipv4 + "--<<<<<<<<<<<<<<<");
				System.out.println("network short name:" + shortName);
				System.out.println("network disable name:" + displayName);
				System.out.println("network mac:" + mac);
				System.out.println("network ip:" + ipv4);
				
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				
				hosts.add(HostInfo.builder().shortName(shortName).displayName(displayName).ipv4(ipv4).mac(mac).build());
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		if(null != hosts && hosts.size()>0) {
			Optional<HostInfo> opt = hosts.stream().filter(item -> item.getIpv4().startsWith(INTRANET_IP_PREFIX)).findFirst();
			if(opt.isPresent()) {
				
				Map<String, Object> map = new HashMap<>();
				map.put(SERVER_ADDRESS, opt.get().getIpv4());
				
				MapPropertySource target = new MapPropertySource(DEFAULT_PROPERTIES, map);
		        // addFirst表示这个属性的优先级最高，会覆盖同名的配置
		        environment.getPropertySources().addFirst(target);
			}
		}
	}
	
	/**
	 * 获取IP V4的地址
	 * @param net
	 * @return
	 * @throws SocketException
	 */
	private String getIpv4(NetworkInterface net) throws SocketException {
		String result = null;
		Enumeration<InetAddress> address = net.getInetAddresses();
		while(address.hasMoreElements()) {
			InetAddress addr = address.nextElement();
			String ip = addr.getHostAddress();
			if(ip.contains(":")) {
				// System.out.println("this ip is ip v6 addrress : " + ip);
				continue;
			}
			result = ip;
		}
		return result;
	}

	/**
	 * 获取mac地址
	 * @param net
	 * @return
	 * @throws SocketException
	 */
	private String getMac(NetworkInterface net) throws SocketException {
		StringBuilder sb = new StringBuilder();
		
		byte[] bytes = net.getHardwareAddress();
		if (bytes != null) {
			for (int i = 0; i < bytes.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				int tmp = bytes[i] & 0xff; // 字节转换为整数
				String str = Integer.toHexString(tmp);
				if (str.length() == 1) {
					sb.append("0").append(str);
				} else {
					sb.append(str);
				}
			}
			return sb.toString();
		}
		return null;
	}
	
	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	static class HostInfo {
		private String shortName;
		
		private String displayName;
		
		private String ipv4;
		
		private String mac;
	}
}
