# Peer-2-Peer Implementation

The Peer-2-Peer implementation works by first parsing input to get the host and port adress.
After which the program attempts to connect the host/port combination.
If the connection is accepted then the program assumes its a client and follows the clien implementaion.
Otherwise the main thread catches and IOException which and therefore knows that there is no server and executes the code from the Server from the Client/Server implementaion but only accepts one session.

Representation of the Peer-2-Peer connection:


![Peer-2-Peer](https://raw.githubusercontent.com/michaelrinos/Connect-Fout-Client-Server/tree/master/images/P2P.png)

