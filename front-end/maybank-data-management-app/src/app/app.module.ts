import { AppComponent } from "./app.component";
import { UserManagementComponent } from "./user-management/user-management.component";
import { DataManagementComponent } from "./data-management/data-management.component";
import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { AppRoutingModule } from "./app.routes";
import { HTTP_INTERCEPTORS, HttpClient, provideHttpClient } from "@angular/common/http";
import { FormsModule } from "@angular/forms";
import { AuthInterceptor } from "./auth.interceptor";
import { DataService } from "./data.service";

@NgModule({
    
    imports:[
        BrowserModule,
        AppRoutingModule,
        FormsModule
    ],
    providers:[{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},DataService]
})
export class AppModule{}