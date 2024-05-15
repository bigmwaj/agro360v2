import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductionAvicoleModule } from './modules/production/avicole/production.avicole.module';
import { StockModule } from './modules/stock/stock.module';
import { VenteModule } from './modules/vente/vente.module';
import { FinanceModule } from './modules/finance/finance.module';
import { HomeModule } from './modules/home/home.module';
import { PartnerModule } from './modules/core/core.module';
import { AchatVentModule } from './modules/achat-vente/achat-vente.module';
import { AchatModule } from './modules/achat/achat.module';
import { PaieModule } from './modules/paie/paie.module';
import { SystemModule } from './modules/common/system.module';
import { XhrInterceptor } from './modules/common/interceptor/XhrInterceptor';
import { MatMenuModule } from '@angular/material/menu';

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [    
        MatIconModule,    
        MatButtonModule,        
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        CommonModule,
        MatMenuModule,
        HttpClientModule,        
        MatSidenavModule,
        MatToolbarModule,
        MatListModule,
        PartnerModule,
        StockModule,
        AchatVentModule,
        VenteModule,
        AchatModule,
        FinanceModule,
        ProductionAvicoleModule,
        HomeModule,
        PaieModule,
        SystemModule.forRoot({title:'Bienvenue sur Agro360'})
        
    ],
    providers: [
        {
            multi: true,
            provide: HTTP_INTERCEPTORS,
            useClass: XhrInterceptor
        }
    ],
    bootstrap: [AppComponent],

})
export class AppModule {
    
}
