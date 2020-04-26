namespace java com.baopdh.thrift.gen

typedef i32 int
typedef i64 long

struct Message {
	1: string text,
	2: int sender,
	3: long timestamp,
}

enum TASK {
	PUT, DELETE, WARNING
}

struct Operation {
	1: binary key,
	2: binary value,
	3: TASK task
}

service MessageStoreService {
	void ping(),
	Message get(1: int id),
	bool remove(1: int id),
	bool put(1: int id, 2: Message message),
	int getKey(),
}
