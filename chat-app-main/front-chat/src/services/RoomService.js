import { httpClient } from "../config/AxiosHelper";

export const createRoomApi = async (roomDetail) => {
  const respone = await httpClient.post(
    `/api/v1/rooms`,
    { roomId: roomDetail },  // ✅ send as JSON object matching the Room entity
    {
      headers: {
        "Content-Type": "application/json",  // ✅ was "text/plain" — caused the bug
      },
    }
  );
  return respone.data;
};

export const joinChatApi = async (roomId) => {
  const response = await httpClient.get(`/api/v1/rooms/${roomId}`);
  return response.data;
};

export const getMessagess = async (roomId, size = 50, page = 0) => {
  const response = await httpClient.get(
    `/api/v1/rooms/${roomId}/messages?size=${size}&page=${page}`
  );
  return response.data;
};
