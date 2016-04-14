#ConnectFourTCP

This version of the Connect Four game is identical to the original version of the ConnectFourTCP game with the exception of the message
encoding used to transmit the messages. Instead of using Binary encoding the program uses Textual encoding.

|			Textual Encoding 	    |			Binary Encoding	   		|		Object Encoding		   	    |
|---------------------------------------------------|---------------------------------------------------|---------------------------------------------------|
| Texts strings sent over the network		    | Raw Bytes sent over the network 	      		| Serialized Java objects sent over the network	    |
| Uses Java.io.PrintStream to send   		    | Uses Java.io.DataOutputStream to send   		| Uses Java.io.ObjectOutputStream to send	    |
| Uses Java.util.Scanner to recieve  		    | Uses Java.io.DataInputStream to recieve 		| Uses Java.io.ObjectInputStream to recieve	    |
| Human readable messages	     		    | Minimizes message sizes		      		| Object oriented				    |
| Larger message sizes then Binary   		    | Not human readable 		      		| Much larger and more time to encode/decode	    |
| Used by Internet protocols such as HTTP, SMTP, ...| Used by Internet protocols such as DNS  		| Not used by Internet protocols		    |


