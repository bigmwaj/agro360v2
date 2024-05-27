import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexPageComponent } from './dashboard/index.page.component';
import { LoginPageComponent } from './index/login.page.component';
import { ProfilPageComponent } from './index/profil.page.component';
import { AuthGuard } from '../common/guard/auth.guard';

const routes: Routes = [
    { 
        path: '',
        canActivate: [AuthGuard], 
        children:[
            { path: '', component: IndexPageComponent },
            { path: 'home', component: IndexPageComponent },
            { path: 'login', component: LoginPageComponent },
            { path: 'profil', component: ProfilPageComponent },
        ]
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule { }
