
export interface BaseEntity {
  id: string;
  name: string;
  description: string;
  status: string;
  updateBy: string;
  updateAt: string;
  createBy: string;
  createAt: string;
}

export interface BaseName {
  id: string;
  name: string;
}

export interface FormProps<T , V> {
  initialData?: T | null;
  onSubmit: (data: V) => void | Promise<void>;
  onClose: () => void;
}

export interface TableProp<T> {
  data: T[];
  loading: boolean;
  onEdit: (item: T) => void;
  onDelete: (id: string) => void;
  totalElements: number
  goToPage: (pageNumber: number) => void;
  page: number;
  totalPages: number;
  setPage: (page: number) => void;
}

export interface Warehouse extends BaseEntity{
  address: string;
}

export interface Inventory extends BaseEntity {
  productName: string;
  quantityAvailable: number;
  warehouseName: string;
  minimumQuantity: number;
  maximumQuantity: number;
  productBatchName: string;
  expiryDate: string;
  importDate: string;
  priceNormal: number;
}

// Product
export interface Product extends BaseEntity {
  priceNormal: number;
  brandName: string;
  categoryName: string;
  manufacturingLocationName: string;
  sku: string;
  tag: string;
  priceSell: number;
  promotionPrice: number;
  metaKeyword: string;
  seoTitle: string;
  brandId: string;
  categoryId: string;
  manufacturingLocationId: string;
  weight: number;
  vat: number;
}

export interface ProductFormData extends Product {
  id: string;
  name: string;
  description: string;
  priceNormal: number;
  status: string;
  brandName: string;
  categoryName: string;
  manufacturingLocationName: string;
  sku: string;
  tag: string;
  priceSell: number;
  promotionPrice: number;
  metaKeyword: string;
  seoTitle: string;
  updateBy: string;
  brandId: string;
  categoryId: string;
  manufacturingLocationId: string;
}

export interface ProductStaticData {
  totalElements: number;
  countProducActive: number;
  countCategoryActive: number;
  countBrandActive: number;
}

export type ProductBatch = BaseName;
export type CategoryName = BaseName;
export type BrandName = BaseName;
export type ManufacturingLocationName = BaseName;

export type ProductFormProps = FormProps<Product, ProductFormData>;

// Category
export interface Category extends BaseEntity {
  metaKeyword: string;
  seoTitle: string;
  smallImage: string;
  parentId: string;
}
export interface CategoryFormData {
  id: string;
  name: string;
  description: string;
  status: string;
  metaKeyword: string;
  seoTitle: string;
  updateBy: string;
}

export type CategoryFormProps = FormProps<Category, CategoryFormData>;

export type CategoryTableProp = TableProp<Category>;

// Brand
export interface Brand extends BaseEntity {
  country: string;
}

export interface BrandFormData extends Omit<BaseEntity, "createAt" | "updateAt"> {
  country: string;
}

export type BrandFormProps = FormProps<Brand, BrandFormData>;

// Supplier
export interface Supplier extends BaseEntity {
  email: string;
  address: string;
  phone: string;
}

export interface SupplierFormData extends Omit<BaseEntity, "createAt" | "updateAt"> {
  email: string;
  address: string;
  phone: string;
}

export type SupplierTableProp = TableProp<Supplier>
export type SupplierFormProp = FormProps<Supplier, SupplierFormData>;

export interface InventoryTable extends Omit<TableProp<Inventory>, "onDelete" | "onEdit"> {

}