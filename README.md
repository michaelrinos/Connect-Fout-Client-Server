# Connect Four

Connect Four is a network based game implemented using both TCP and UDP protocols to communicate.
By default both the TCP and UDP implementations use Binary encoding to transmit the information from the client to the server. The original purpose of this project is based on the course work for parallel and distributed systems, which was a two-part project. Part one involved creating a TCP based client for the game of Nim. The second part involved converting the Nim client to UDP and implementing our own server. Using my knowledge I remade the Nim, based Client/Server to a Connect Four Client/Server game by changing the UI and converting the backend to support the new UI. Then I took it a step further and remade the project so that it would use only TCP.

The main points of this project are:
- Java
- Threading 
- MVC
- Swing
- Network Communication (TCP and UDP)


### Client Server Implementation

The Server works by first parsing the input to get the host and port adress. 
The Server then infinitley loops so that multiple sessions may be started.
Within the loop the main thread gets blocked by the serversockets accept method which blocks until a client connects.
At which point the server checks if that client has previously connected to the server, and if it hasn't it creates a new instance of the 
viewproxy object which is responsible for recieving messages from the client and reporting them to the underlying boardmodel (Except for the "Join" message which is reported to the SessionManager).
It then sets the SessionManager as the proxy's ViewListener. The SessionManager has one job, it calls the "Join" method which determines if there is an open session or not. If there isn't one, a new ConnectModel instance is created and the sessionmanger waits unitl another session is available. Once the second client connects the Sessionmanager links the second client to the same ConnectModel that the first client is attached two and the game can commence.

Representation of how the Client and Server communicate:

![Client/Server](https://raw.githubusercontent.com/michaelrinos/Connect-Fout-Client-Server/master/ClSe.png)

Representation of SessionManager:

![Session Manager](https://raw.githubusercontent.com/michaelrinos/Connect-Fout-Client-Server/master/SessionManager.png)



