import {RoomInfo} from 'umi';

export const genRoomId = (user1: number, user2: number) => {
    return user1 < user2 ? `${user1}${user2}` : `${user2}${user1}`;
}

export const getAnotherUser = (user1: number, roomId: string) => {
    const stringId = user1.toString();
    const index = roomId.indexOf(stringId);
    return index === 0
        ? parseInt(roomId.substring(stringId.length))
        : parseInt(roomId.substring(0, stringId.length));
}

export const moveFirst = (list: string[], element: string) => {
    const index = list.indexOf(element);
    if (index > -1) {
        list.splice(index, 1);
    }
    list.unshift(element);
    return list;
}

export const sortChatBoxes = ({room, list}: {room: {[id: string]: RoomInfo}, list: string[]},
                                l: number, r: number) => {
                                    console.log('zz sort ne');
    let i = l, j = r, pivot = room[list[(l + r) >> 1]].timeStamp;
    while (i <= j) {
        while (room[list[i]].timeStamp > pivot) ++i;
        while (room[list[j]].timeStamp < pivot) --j;
        if (i <= j) {
            if (i < j) {
                let tmp = list[i];
                list[i] = list[j];
                list[j] = tmp;
            }
            ++i;
            --j;
        }
    }
    if (l < j) sortChatBoxes({room, list}, l, j);
    if (i < r) sortChatBoxes({room,list}, i, r);
}