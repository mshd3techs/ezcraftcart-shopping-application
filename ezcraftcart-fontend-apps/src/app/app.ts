import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/components/header/header.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { CartDrawerComponent } from './shared/components/cart-drawer/cart-drawer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, FooterComponent, CartDrawerComponent],
  template: `
    <div class="min-h-screen flex flex-col">
      <app-header />
      <div class="flex-1">
        <router-outlet />
      </div>
      <app-footer />
      <app-cart-drawer />
    </div>
  `,
  styles: []
})
export class App { }
