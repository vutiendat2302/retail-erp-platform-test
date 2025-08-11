import api from "./api";

export const getManufacturingLocations = () => api.get('/api/manufacturingLocation');