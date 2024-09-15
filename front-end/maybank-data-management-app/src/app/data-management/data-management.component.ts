import { Component, Inject, inject, OnInit } from '@angular/core';
import { Data, Responses } from '../data';
import { DOCUMENT, NgFor } from '@angular/common';
import { DataService } from '../data.service';
import { AuthService } from '../auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NgIf } from '@angular/common';
@Component({
  selector: 'app-data-management',
  standalone: true,
  imports: [NgFor],
  templateUrl: './data-management.component.html',
  styleUrl: './data-management.component.css'
})
export class DataManagementComponent implements OnInit{
  datas: Responses;
  token: string;
  public showElement: boolean =true;
isDisplay: any;

  constructor(private dataService: DataService, private route:ActivatedRoute, @Inject(DOCUMENT) document:Document){

  }
  ngOnInit(): void {
    this.getDatas();
  }
  getDatas() {

    this.dataService.getDatas(this.dataService.getToken()).subscribe(data=>{
      this.datas=data;
    });
  }

  authService = inject(AuthService);
  router = inject(Router);
  public logout(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  public editButtonTrigger(id:number,index:number){
    console.log("I AM HERE"+index);
    console.log("I AM HERE"+"trxAmmount"+index);
    var x1=document.getElementById("trxAmmount"+index);
    var x2=document.getElementById("description"+index);
    var x3=document.getElementById("trxDate"+index);
    var x4=document.getElementById("time"+index);
    var x5=document.getElementById("customerId"+index);
    var x6=document.getElementById("id"+index);
    var x7=document.getElementById("accountNumber"+index);

    var y1=document.getElementById("ttrxAmmount"+index);
    var y2=document.getElementById("tdescription"+index);
    var y3=document.getElementById("ttrxDate"+index);
    var y4=document.getElementById("ttime"+index);
    var y5=document.getElementById("tcustomerId"+index);
    var y6=document.getElementById("tid"+index);
    var y7=document.getElementById("taccountNumber"+index);
    console.log("I AM HERE");
    console.log(x1,x2,x3,x4,x5,x6,x7);
    if(x1!=null && x2!=null && x3!=null && x4!=null && x5!=null && x6!=null && x7!=null){
       x1.style.visibility="hidden";   
       x2.style.visibility="hidden";   
       x3.style.visibility="hidden";   
       x4.style.visibility="hidden";   
       x5.style.visibility="hidden";   
       x6.style.visibility="hidden";   
       x7.style.visibility="hidden";      
    }

    if(y1!=null && y2!=null && y3!=null && y4!=null && y5!=null && y6!=null && y7!=null){
      y1.style.visibility="visible";   
      y2.style.visibility="visible";   
      y3.style.visibility="visible";   
      y4.style.visibility="visible";   
      y5.style.visibility="visible";   
      y6.style.visibility="visible";   
      y7.style.visibility="visible";      
   }
    return this.datas.content;
  }

  public deleteById(id:number,index:number){
  
   this.deleteDataById(id,index);
  }

  deleteDataById(id:number,index:number) {

    this.dataService.deleteData(id).subscribe(data=>{
      console.log(data);
      this.datas.content.splice(index,1);
    })
      
  }

}
