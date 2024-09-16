import { Component, OnInit } from '@angular/core';
import { inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';
import { DataService } from '../data.service';
@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './user-management.component.html',
  styleUrl: './user-management.component.css'
})
export class UserManagementComponent{
  username: string = '';
  password: string = '';

  authService = inject(AuthService);
  router = inject(Router);

  protected loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  })

  onSubmit(){
    if(this.loginForm.valid){
      console.log(this.loginForm.value);
      this.authService.login(this.loginForm.value)
      .subscribe((data: any) => {
        if(this.authService.isLoggedIn()){
          // this.setToken(data.token);
          this.dataService.setToken(data.token);
          this.router.navigate(['/datapage']);
        }
        console.log(data);
      });
    }
  }
  constructor(private dataService:DataService){
    
  }

  
}
