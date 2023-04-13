import socket
import json
from underthesea import sentiment
LOCALHOST = '192.168.1.6'
port = 9990
def process_data(message_user):
    total = len(message_user)
    postive = 0
    none = 0
    negative = 0
    for i in message_user:
        tmp = str(sentiment(i))
        if tmp == 'None':
            none += 1
        elif tmp == 'positive':
            postive += 1
        else: negative += 1
    arr = [postive , none , negative]
    json_string = json.dumps(arr)
    print(str(total))
    return json_string

def create_server():
    server_socket = socket.socket(socket.AF_INET,socket.SOCK_STREAM) 
    server_socket.bind((LOCALHOST,port))
    server_socket.listen(5)
    
    print("Server started...")
    client_sockets,addr=server_socket.accept()
    client_sockets.send("xin chào".encode("utf-8"))
    message_user = ""
    while True:
        msg_received = client_sockets.recv(1024)
        msg_received = msg_received.decode("utf-8")
        message_user = json.loads(msg_received)
        print(msg_received)
        print("Receive data")
        break

    client_sockets.sendall(process_data(message_user).encode("utf-8"))

        


if __name__ == "__main__":
    create_server()