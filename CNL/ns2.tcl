#1.create ns object
set ns [new Simulator]

#2.Give colour code
$ns color 1 Orange
$ns color 2 Black

#3.Open Nam file
set nf [open out.nam w]
$ns namtrace-all $nf

#4.Write finish procedure
proc finish{}
{
	global ns nf
	$ns flush-trace
	close $nf
	exec nam out.nam &
	exit 0
}

#5.Setting nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

#6.Connect Links
$ns duplex-link $n0 $n2 2Mb 10ms DropTail
$ns duplex-link $n1 $n2 2Mb 10ms DropTail
$ns duplex-link $n2 $n3 1.7Mb 20ms DropTail

#7.Set Queue limit 10
$ns queue-limit $n2 $n3 10

#8.Set node position
$ns duplex-limit-op $n0 $n2 orient right-down
$ns duplex-limit-op $n1 $n2 orient right-up
$ns duplex-limit-op $n2 $n3 orient right

#9.Monitor queue
$ns duplex-limit-op $n2 $n3 queuePos 0.5

#10.set TCP and sink
set tcp [new Agent/TCP}
$tcp set class_ 2
$ns attach-agent $n0 $tcp
set sink [new Agent/TCPSink]
$ns attach-agent $n3 $sink
$ns connect $tcp $sink
$tcp set fid_ 1

#11.Set FTP over TCP
set ftp [new Application/FTP]
$ftp attach-agent $tcp
$ftp set type_ FTP

#12.set UDP and NULL
set udp [new Agent/UDP]
$ns attach-agent $n1 $udp
set null [new Agent/NULL]
$ns attach-agent $n3 $udp
$ns connect $udp $null
$udp set fid_ 2

#13.set CBR over UDP
set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set type_ CBR
$cbr set packet_size_ 1000                      #IMPPPPP
$cbr set rate_ 1mb
$cbr set random_ false

#14.Schedule 
$ns at 0.1 "$cbr start"
$ns at 1.0 "$ftp start"
$ns at 4.0 "$ftp stop"
$ns at 4.5 "$cbr stop"

#15.Detach TCP and sink
$ns at 4.5 "$ns detach-agent $n0 $tcp ; $ns detach-agent $n3 $sink"

#16.finish
$ns at 5.0 "finish"

#17.Display CBR package size and interval
puts "CBR Package size = [$cbr set packet_size_]"
puts "CBR Interval = [$cbr set interval_]"

#18.run Simulator
$ns run




