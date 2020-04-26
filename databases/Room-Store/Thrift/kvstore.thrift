namespace java com.baopdh.thrift.gen

typedef i32 int
typedef i64 long

struct Room {
	1: int user1,
	2: int user2,
	3: string lastMessage,
	4: long timestamp
}

enum TASK {
	PUT, DELETE, WARNING
}

struct Operation {
	1: binary key,
	2: binary value,
	3: TASK task
}

service RoomStoreService {
	void ping(),
	Room get(1: string id),
	bool remove(1: string id),
	bool put(1: string id, 2: Room room),
	string getKey(),
}
