import { Component, ElementRef, EventEmitter, Inject, inject, Input, NgModule, OnInit, Output, QueryList, ViewChild } from '@angular/core';
import { Data, Pageable, Responses } from '../data';
import { DOCUMENT, NgFor, NgIf } from '@angular/common';
import { DataService } from '../data.service';
import { AuthService } from '../auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PaginationComponent } from "../pagination/pagination.component"; 
import { FormsModule, NgModel } from '@angular/forms';
@Component({
  selector: 'app-data-management',
  standalone: true,
  imports: [NgFor, PaginationComponent,NgIf,FormsModule],
  templateUrl: './data-management.component.html',
  styleUrl: './data-management.component.css'
})
export class DataManagementComponent implements OnInit{
  toDisplay=false;
  indx=0;
  temp:Data[];
  paginatedData: Pageable;
  datas: Responses;
  token: string;
  public showElement: boolean =true;
  isDisplay: any;
  totalPages:number;
  @Input() currentPage: number;
  @Input() itemsPerPage: number;
  @Input() totalItems: number;
  @Output() pageChanged: EventEmitter<number> = new EventEmitter();

  get totalPage(): number {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  constructor(private dataService: DataService, private route:ActivatedRoute, @Inject(DOCUMENT) document:Document){

  }
  ngOnInit(): void {
    this.getDatas();
  }
  getDatas() {

    this.dataService.getDatas(this.dataService.getToken(),this.currentPage, this.itemsPerPage).subscribe(data=>{
      this.datas=data;
      this.paginatedData = data.pageable;
      this.totalPages = data.totalPages;
      this.currentPage=data.pageable.pageNumber;
      this.itemsPerPage=data.pageable.pageSize;
      this.dataService.setContent(data.content);
    });
  }

  authService = inject(AuthService);
  router = inject(Router);
  
  public logout(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  public deleteById(id:number,index:number){
  
   this.deleteDataById(id,index);
  }

  deleteDataById(id:number,index:number) {

    this.dataService.deleteData(id).subscribe(data=>{
      alert("successfully deleted..");
      this.datas.content.splice(index,1);
      this.dataService.setContent(this.datas.content);
    })
      
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.getDatas();
      this.pageChanged.emit(page);
    }
  }
    
    searchBy() {
      this.router.navigate(['/searchpage']);
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
