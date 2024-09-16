export class Data {
    id: number;
    accountNumber: string;
    trxAmmount: Number;
    description: string;
    trxDate: string;
    time: string;
    customerId: string;
isEdit: boolean;
}

export class Responses{
  content:Data[];
  totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    sort: Sorts;
    first:boolean;
    last:boolean;
    numberOfElements:number;
    pageable: Pageable;
    empty:boolean;

}

export class Sorts{
  empty:boolean;
  unsorted:boolean;
  sorted:boolean;
}

export class Pageable{
  pageNumber:number;
  pageSize:number;
  sort:Sorts;
  offset:number;
  unpaged:boolean;
  paged:boolean;
}