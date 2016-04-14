# Connect Four

Connect Four is a network based game implemented as aClient/Server implementation and Peer-2-Peer Implementation. The network game uses both TCP and UDP protocols to communicate, as well as both Binary and Textual encoding methods to communicate the messages.

### Client Server Implementation

The Server works by first parsing the input to get the host and port adress. 
The Server then infinitley loops so that multiple sessions may be started.
Within the loop the main thread gets blocked by the serversockets accept method which blocks until a client connects.
At which point the server checks if that client has previously connected to the server, and if it hasn't it creates a new instance of the 
viewproxy object which is responsible for recieving messages from the client and reporting them to the underlying boardmodel (Except for the "Join" message which is reported to the SessionManager).
It then sets the SessionManager as the proxy's ViewListener. The SessionManager has one job, it calls the "Join" method which determines if there is an open session or not. If there isn't one, a new ConnectModel instance is created and the sessionmanger waits unitl another session is available. Once the second client connects the Sessionmanager links the second client to the same ConnectModel that the first client is attached two and the game can commence.

Representation of SessionManager:

<div style="text-align:center">
[https://raw.githubusercontent.com/michaelrinos/Connect-Fout-Client-Server/master/fig9small.png]
</div>

### Peer-2-Peer Implementation

The Peer-2-Peer implementation works by first parsing input to get the host and port adress.
After which the program attempts to connect the host/port combination.
If the connection is accepted then the program assumes its a client and follows the clien implementaion.
Otherwise the main thread catches and IOException which and therefore knows that there is no server and executes the code from the Server from the Client/Server implementaion but only accepts one session.

Representation of the Peer-2-Peer connection:

<div style="text-align:center">
[https://raw.githubusercontent.com/michaelrinos/Connect-Fout-Client-Server/master/fig10.png]
</div>

