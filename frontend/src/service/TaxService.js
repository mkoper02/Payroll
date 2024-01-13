import api from "../api/axios";

export const listTaxes = () => api.get(`/tax`);

export const deleteTax = (taxId) => api.delete(`/tax/${taxId}`);