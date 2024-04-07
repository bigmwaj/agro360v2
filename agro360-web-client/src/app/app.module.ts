import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
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
import { UIService } from './common/service/ui.service';
import { ProductionAvicoleModule } from './modules/production/avicole/production.avicole.module';
import { StockModule } from './modules/stock/stock.module';
import { VenteModule } from './modules/vente/vente.module';
import { FinanceModule } from './modules/finance/finance.module';
import { HomeModule } from './modules/home/home.module';
import { PartnerModule } from './modules/core/core.module';
import { AchatVentModule } from './modules/achat-vente/achat-vente.module';
import { AchatModule } from './modules/achat/achat.module';
import { PaieModule } from './modules/paie/paie.module';

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
        PaieModule
    ],
    providers: [
        UIService
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
