"""Custom topology example
Two di
rectly connected switches plus a host for each switch:
Adding the 'topos' dict with a key/value pair to generate our newly defined
topology enables one to pass in 
topo=mytopo' from the command line.
"""

from mininet.topo import Topo

class h2s4( Topo ):
	"Simple topology example."
	
	def __init__( self ):
		"Create custom topo."

		# Initialize topology
		Topo.__init__( self )

		# Add hosts and switches
		h1host = self.addHost( 'h1' )
		h3host = self.addHost( 'h3' )
		s1switch = self.addSwitch( 's1' )
		s2switch = self.addSwitch( 's2' )
		s3switch = self.addSwitch( 's3' )
		s4switch = self.addSwitch( 's4' )

		# Add links
		self.addLink( h1host, s1switch )
		self.addLink( s1switch, s4switch )
		self.addLink( s1switch, s3switch )
		self.addLink( s1switch, s2switch )
		self.addLink( s4switch, s3switch )
		self.addLink( s4switch, s2switch )
		self.addLink( s2switch, s3switch )
		self.addLink( s3switch, h3host )

topos = { 'h2s4': ( lambda: h2s4() ) }
