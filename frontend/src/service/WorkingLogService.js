import api from "../api/axios";

export const listWorkingLogs = (employeeId) =>api.get(`/employee/${employeeId}/workinglogs`);
