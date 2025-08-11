import api from "./api";

export const getBrands = () => api.get('/api/brand');

export const getBrand = (id: string) => api.get( `/api/brand/${id}`);

export type BrandRequestDto = {
    id: string;
    name: string;
}