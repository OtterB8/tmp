namespace java com.baopdh.thrift.gen

typedef i32 int

struct Tracking {
	1: list<int> keys
}

enum TASK {
	PUT, DELETE, WARNING
}

struct Operation {
	1: binary key,
	2: binary value,
	3: TASK task
}

service TrackingStoreService {
	void ping(),
	Tracking get(1: string id),
	bool remove(1: string id),
	bool put(1: string id, 2: Tracking tracking), 
	string getKey(),
}
