import React, {useState, useEffect} from 'react';

interface Warehouse {
  id: string;
  name: string;
  description: string;
  address: string;
  status: string;
}

interface WarehouseTable {
  data: Warehouse[];
}

