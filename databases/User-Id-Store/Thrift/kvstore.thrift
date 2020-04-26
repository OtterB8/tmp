namespace java com.baopdh.thrift.gen

typedef i32 int

struct UserIdentity {
	1: int key
}

enum TASK {
	PUT, DELETE, WARNING
}

struct Operation {
	1: binary key,
	2: binary value,
	3: TASK task
}

service UserIdStoreService {
	void ping(),
	UserIdentity get(1: string id),
	bool remove(1: string id),
	bool put(1: string id, 2: UserIdentity userIdentity), 
	string getKey(),
}
