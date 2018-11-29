package pl.edu.agh.kt;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.packet.ARP;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.TCP;
import net.floodlightcontroller.packet.UDP;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PacketExtractor {
	public PacketExtractor() {
	}

	private static final Logger Logger = LoggerFactory
			.getLogger(PacketExtractor.class);
	private FloodlightContext cntx;
	private OFMessage msg;
	protected IFloodlightProviderService floodlightProvider;
	private Ethernet eth;
	private IPv4 ipv4;
	private ARP arp;
	private TCP tcp;
	private UDP udp;

	public void packetExtract(FloodlightContext cntx) {
		this.cntx = cntx;
		extractEth();
	}

	public void extractEth() {
		eth = IFloodlightProviderService.bcStore.get(cntx,
			IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		Logger.info("Frame: src mac {}", eth.getSourceMACAddress());
		Logger.info("Frame: dst mac {}", eth.getDestinationMACAddress());
		Logger.info("Frame: ether_type {}", eth.getEtherType());

		if (eth.getEtherType() == EthType.ARP) {
			arp = (ARP) eth.getPayload();
			//extractArp();
		}

		if (eth.getEtherType() == EthType.IPv4) {
			ipv4 = (IPv4) eth.getPayload();
			extractIPv4();
		}
	}

	public void extractIPv4() {
		Logger.info("L3 packet: src ip {}", ipv4.getSourceAddress());
		Logger.info("L3 packet: dst ip {}", ipv4.getDestinationAddress());
		Logger.info("L3 packet: TTL {}", ipv4.getTtl());
		Logger.info("L3 packet: L4_protocol {}", ipv4.getProtocol());

		if (ipv4.getProtocol() == IpProtocol.TCP) {
			tcp = (TCP) ipv4.getPayload();
			//extractTCP();
		}

		if (ipv4.getProtocol() == IpProtocol.UDP) {
			udp = (UDP) ipv4.getPayload();
			//extractUDP();
		}
	}
}