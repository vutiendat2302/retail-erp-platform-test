import React, {useState, useEffect} from 'react';

import { Button } from '../components/ui/button';

import ProductStatic from '../components/inventory_components/products/ProductStatic';
import { ProductSearch } from '../components/inventory_components/products/ProductSearch';
import { WarehouseChoose } from '../components/inventory_components/warehouses/WarehouseChoose';
import { getWarehouses } from '../services/inventery-api/WarehouseService';

interface Warehouse {
  id: string;
  name: string;
  description: string;
  address: string;
  status: string;
  createBy: string;
  updateBy: string;
}

const Warehouse: React.FC = () => {
  const [dataWarehouse, setDataWarehouse] = useState<Warehouse[]>([]);
  const [openFindWarehouse, setOpenWarehouse] = useState(false);
  const [selectWarehouse, setSelectWarehouse] = useState<string | null>('');

  useEffect(() => {
    const fetchWarehouses = async () => {
      try {
        const response = await getWarehouses();
        const warehouseData: Warehouse[] = response.data;
        setDataWarehouse(warehouseData);

        if (warehouseData.length > 0) {
          setSelectWarehouse(warehouseData[0].id);
        }
      } catch (error) {
      console.error("Failed to fetch warehouse data: ", error);
      }
    };

    fetchWarehouses();
  }, []);

  return (
    <div>
      <div className='px-6 md:px-10 -mt-10 space-y-6'>
        <div className='flex items-center justify-between '>
          <div className='mb-2'>
            <h3 className='mb-6'>Quản lý kho hàng</h3>
            <p className="text-muted-foreground">
              Theo dõi và quản lý tồn kho của bạn
            </p>
          </div>
        
          <div>
            <WarehouseChoose
              selectWarehouse={selectWarehouse}
              setSelectWarehouse={setSelectWarehouse}
              openFindWarehouse={openFindWarehouse}
              setOpenWarehouse={setOpenWarehouse}
              dataWarehouse={dataWarehouse}
            />
          </div>
        </div>

        <div>
          <ProductStatic/>
        </div>

        <div>
          <ProductSearch />
        </div>
      </div>
    </div>
  );
};
export default Warehouse;