# Network Management

Data management simulation program using [FCAPS](https://searchnetworking.techtarget.com/definition/FCAPS) model.<br/>
[Advanced IP Scanner](https://www.advanced-ip-scanner.com), [Wireshark](https://www.wireshark.org/), [Wireless NetView](https://www.nirsoft.net/utils/wireless_network_view.html) and [Xirrus Wifi Inspector](https://www.riverbed.com/gb/products/xirrus/inspector.html) were used in order to collect network's data.<br/><br/>
The following tasks have been checked:
* `Access Point Fault:` Records access points that do not work. These are the ones that have zero last and average signal.
* `DHCP failure:` Looking for ip conflicts, i.e. if the same ip address has been given to more than one connected device at the same access point.
* `Weak signal:` Displays the access points that have a low signal (< -70 dBm) and the connection to them won't be good.
* `Signal Analysis:` Analyzes the signal quality of access points by taking the power in dBm and finding the percentage of power using the formula: 2 * (100 + rssi) 
* `Connected devices:` The devices that are connected to the access points from which we collected data are presented.
* `Packet analysis:` Recording the number and nature of packets circulating on the network.
* `Retransmissions/Lost segments:` Retransmissions and lost TCP packets on the network we are considering.
* `Authentication:` Lists the access points that don't use WPA2 authentication.
* `Encryption:` The most modern encryption protocol is CCMP. We have noted the rest as unacceptable.
* `Channel analysis:` Distribution of access points on channels 1 to 13.
* `Channel assignment:` The access points are reassigned to the non-overlapping channels 1, 6 and 11, so that we have the least possible interference.
* `DDos attack:` The data has been collected at different times and while we were connected to different networks. For this reason, we consider an attack when there are 80 or more packets of the same protocol sent to the same IP address and port.
 
<br/>A MySQL database have been created to store the data from these four sources. There  GUI for inserting data in xml or csv format and a GUI with the common network problems based on FCAPS. <br/> The database with data is available <a href="https://www.dropbox.com/s/jyusjwhq400vylg/Dump20160726.sql?dl=0S">here</a>.
