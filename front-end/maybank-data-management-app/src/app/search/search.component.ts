import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { DataService } from '../data.service';
import { Data, Pageable, Responses } from '../data';
import { AuthService } from '../auth.service';
import { DOCUMENT, NgFor, NgIf } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
@Component({
  selector: 'app-search',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule,NgFor,FormsModule,NgIf],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent implements OnInit{
  toDisplay=false;
  indx=0;
  paginatedData: Pageable;
  @Input() datas: Responses;
  token: string;
  public showElement: boolean =true;
  isDisplay: any;
  totalPages:number;
  @Input() currentPage: number;
  @Input() itemsPerPage: number;
  @Input() totalItems: number;
  @Output() pageChanged: EventEmitter<number> = new EventEmitter();
  valuez:any;

  authService = inject(AuthService);
  router = inject(Router);

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  get totalPage(): number {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }



  dataService = inject(DataService);
 
onSubmit() {

  if (this.searchForm.valid) {
    this.valuez=this.searchForm.value;
    console.log(this.searchForm.value);
    this.dataService.searchByValue(this.searchForm.value,this.dataService.getToken(), this.currentPage,this.itemsPerPage)
      .subscribe(data=> {
         this.datas=data;
         this.datas.content=data.content;
      this.paginatedData = data.pageable;
      this.totalPages = data.totalPages;
      this.currentPage=data.pageable.pageNumber;
      this.itemsPerPage=data.pageable.pageSize;
      });
  }
   return this.datas;
}
public searchForm = new FormGroup({
  key: new FormControl('', [Validators.required]),
  type: new FormControl('', [Validators.required]),
})

public deleteById(id:number,index:number){
  
  this.deleteDataById(id,index);
 }

 deleteDataById(id:number,index:number) {

   this.dataService.deleteData(id).subscribe(data=>{
     console.log(data);
     this.datas.content.splice(index,1);
   })
     
 }

 getDatas() {

  this.dataService.searchByValue( this.valuez,this.dataService.getToken(),this.currentPage, this.itemsPerPage).subscribe(data=>{
    this.datas=data;
    this.paginatedData = data.pageable;
    this.totalPages = data.totalPages;
    this.currentPage=data.pageable.pageNumber;
    this.itemsPerPage=data.pageable.pageSize;
  });
}

 changePage(page: number): void {
  if (page >= 1 && page <= this.totalPages) {
    this.currentPage = page;
    this.getDatas();
    this.pageChanged.emit(page);
  }
}
   
   searchBy() {
     this.router.navigate(['/datapage']);
     }

     public logout(){
      this.authService.logout();
      this.router.navigate(['/login']);
    }

    onSave(data: Data) {
      this.dataService.updateData(data).subscribe(data=>{
       alert("successfully updated..");
       this.toDisplay=false;
       this.getDatas();
      })
       }
       onEdit(data: any) {
       data.isEdit=true;

       }
       onChange(index: number) {
            this.toDisplay=true;
            this.indx=index;

         }

}
