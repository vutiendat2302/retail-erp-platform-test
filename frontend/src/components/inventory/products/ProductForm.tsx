import React, { useState, useEffect } from 'react';
import type { ChangeEvent, FormEvent } from 'react';
import { Label } from '../../ui/label';
import { Textarea } from '../../ui/textarea';
import { Button } from '../../ui/button';
import { getBrandName, getCategoryName, getManufacturingName } from '../../../services/inventery-api/ProductService';
import type { ProductFormData, ProductFormProps, CategoryName, BrandName, ManufacturingLocationName } from '../../../types/InventoryServiceType';

const ProductForm: React.FC<ProductFormProps> = ({ initialData, onSubmit, onClose }) => {
  const [formData, setFormData] = useState<ProductFormData>({
    id: '',
    name: '',
    description: '',
    priceNormal: 0,
    brandName: '',
    categoryName: '',
    manufacturingLocationName: '',
    manufacturingLocationId: '',
    status: 'inactive',
    sku: '',
    tag: '',
    priceSell: 0,
    promotionPrice: 0,
    metaKeyword: '',
    seoTitle: '',
    updateBy: '',
    brandId: '',
    categoryId: '',
    weight: 0,
    vat: 0,
    updateAt: '',
    createBy: '',
    createAt: '',
  });

  const [categoryName, setCategoryName] = useState<CategoryName[]>([]);
  const [brandName, setBrandName] = useState<BrandName[]>([]);
  const [manufacturingLocationName, setManufacturingLocationName] = useState<ManufacturingLocationName[]>([]);

  // Fetch category
  useEffect(() => {
    const fetchCategoryName = async () => {
      try {
        const res = await getCategoryName();
        setCategoryName(res.data);
      } catch (error) {
        console.error("Fail to fetch category name:", error);
      }
    };
    fetchCategoryName();
  }, []);

  // Fetch brand
  useEffect(() => {
    const fetchBrandName = async () => {
      try {
        const res = await getBrandName();
        setBrandName(res.data);
      } catch (error) {
        console.error("Fail to fetch brand:", error);
      }
    };
    fetchBrandName();
  }, []);

  // Fetch manufacturing locations
  useEffect(() => {
    const fetchManufacturing = async () => {
      try {
        const res = await getManufacturingName();
        setManufacturingLocationName(res.data);
      } catch (error) {
        console.error("Fail to fetch manufacturing:", error);
      }
    };
    fetchManufacturing();
  }, []);

  // Set initial data if exists
  useEffect(() => {
    if (initialData) {
      setFormData({
        id: initialData.id || '',
        name: initialData.name || '',
        description: initialData.description || '',
        priceNormal: initialData.priceNormal || 0,
        brandName: initialData.brandName || '',
        categoryName: initialData.categoryName || '',
        manufacturingLocationName: initialData.manufacturingLocationName || '',
        manufacturingLocationId: initialData.manufacturingLocationId || '',
        status: initialData.status || 'inactive',
        sku: initialData.sku || '',
        tag: initialData.tag || '',
        priceSell: initialData.priceSell || 0,
        promotionPrice: initialData.promotionPrice || 0,
        metaKeyword: initialData.metaKeyword || '',
        seoTitle: initialData.seoTitle || '',
        updateBy: initialData.updateBy || '',
        brandId: initialData.brandId || '',
        categoryId: initialData.categoryId || '',
        weight: initialData.weight || 0,
        vat: initialData.vat || 0,
        updateAt: initialData.updateAt || '',
        createBy: initialData.createBy || '',
        createAt: initialData.createAt || '',
      });
    }
  }, [initialData]);

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;

    // Handle select changes that affect names
    if (name === "categoryId") {
      const selectedCategory = categoryName.find(cat => cat.id === value);
      setFormData(prev => ({
        ...prev,
        categoryId: value,
        categoryName: selectedCategory?.name || '',
      }));
    } else if (name === "brandId") {
      const selectedBrand = brandName.find(brand => brand.id === value);
      setFormData(prev => ({
        ...prev,
        brandId: value,
        brandName: selectedBrand?.name || '',
      }));
    } else if (name === "manufacturingLocationId") {
      const selectedManu = manufacturingLocationName.find(manu => manu.id === value);
      setFormData(prev => ({
        ...prev,
        manufacturingLocationId: value,
        manufacturingLocationName: selectedManu?.name || '',
      }));
    } else {
      setFormData(prev => ({ ...prev, [name]: value }));
    }
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      {/* Name */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="name">Tên sản phẩm</Label>
          <input
            id="name"
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="Nhập tên sản phẩm"
            required
            className="w-full border px-2 py-1 rounded"
          />
        </div>

        {/* SKU */}
        <div className="space-y-2">
          <Label htmlFor="sku">SKU</Label>
          <input
            id="sku"
            type="text"
            name="sku"
            value={formData.sku}
            onChange={handleChange}
            placeholder="Nhập mã sản phẩm"
            required
            className="w-full border px-2 py-1 rounded"
          />
        </div>

        {/* Price Normal */}
        <div className="space-y-2">
          <Label htmlFor="priceNormal">Price Normal</Label>
          <input
            id="priceNormal"
            type="number"
            name="priceNormal"
            value={formData.priceNormal}
            onChange={handleChange}
            placeholder="Nhập giá sản phẩm"
            required
            className="w-full border px-2 py-1 rounded"
          />
        </div>

        {/* Status */}
        <div className="space-y-2">
          <Label>Status</Label>
          <select
            name="status"
            value={formData.status}
            onChange={handleChange}
            className="w-full border px-2 py-1 rounded"
            required
          >
            <option value="active">Active</option>
            <option value="inactive">Inactive</option>
          </select>
        </div>

        {/* Brand */}
        <div>
          <Label>Brand</Label>
          <select
            name="brandId"
            value={formData.brandId}
            onChange={handleChange}
            className="w-full border px-2 py-1 rounded"
          >
            <option value="">--Chọn brand--</option>
            {brandName.map(brand => (
              <option key={brand.id} value={brand.id}>
                {brand.name}
              </option>
            ))}
          </select>
        </div>

        {/* Category */}
        <div>
          <Label>Category</Label>
          <select
            name="categoryId"
            value={formData.categoryId}
            onChange={handleChange}
            className="w-full border px-2 py-1 rounded"
          >
            <option value="">-- Chọn Category --</option>
            {categoryName.map(cat => (
              <option key={cat.id} value={cat.id}>
                {cat.name}
              </option>
            ))}
          </select>
        </div>

        {/* Manufacturing Location */}
        <div>
          <Label>Manufacturing Location</Label>
          <select
            name="manufacturingLocationId"
            value={formData.manufacturingLocationId}
            onChange={handleChange}
            className="w-full border px-2 py-1 rounded"
          >
            <option value="">-- Chọn Manufacturing --</option>
            {manufacturingLocationName.map(manu => (
              <option key={manu.id} value={manu.id}>
                {manu.name}
              </option>
            ))}
          </select>
        </div>
      </div>

      {/* Description */}
      <div className="space-y-2">
        <Label htmlFor="description">Description</Label>
        <Textarea
          id="description"
          name="description"
          value={formData.description}
          onChange={handleChange}
          placeholder="Nhập mô tả sản phẩm"
          required
          className="w-full border px-2 py-1 rounded"
        />
      </div>

      {/* Buttons */}
      <div className="flex justify-end space-x-2 pt-4">
        <Button type="button" variant="outline" onClick={onClose} className="bg-gray-900 rounded hover:bg-gray-400">
          Hủy
        </Button>

        <Button type="submit" variant="outline" className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">
          {initialData ? 'Cập nhật' : 'Thêm sản phẩm'}
        </Button>
      </div>
    </form>
  );
};

export default ProductForm;
