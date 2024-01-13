import api from "../api/axios";

export const listPositions = () => api.get("/position");
export const listEmployeesAtPosition = (id) => api.get(`/position/${id}/employees`);

export const deletePosition = (positionId) => api.delete(`/position/delete/${positionId}`);
