import { Component, inject, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CartService } from '../../../core/services/cart.service';

@Component({
    selector: 'app-cart-drawer',
    standalone: true,
    imports: [CommonModule, RouterLink],
    template: `
    <!-- Overlay -->
    @if (cartService.isOpen()) {
      <div 
        class="overlay animate-fade-in"
        (click)="cartService.closeCart()">
      </div>
    }

    <!-- Drawer -->
    <aside 
      class="drawer"
      [class.drawer-open]="cartService.isOpen()"
      [class.drawer-closed]="!cartService.isOpen()">
      
      <!-- Header -->
      <div class="flex items-center justify-between p-4 border-b border-border">
        <h2 class="font-display text-xl font-semibold">
          Shopping Cart
          <span class="text-muted-foreground font-normal text-base">
            ({{ cartService.totalItems() }} items)
          </span>
        </h2>
        <button 
          (click)="cartService.closeCart()"
          class="p-2 hover:bg-muted rounded-full transition-colors"
          aria-label="Close cart">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>

      <!-- Cart Items -->
      <div class="flex-1 overflow-y-auto p-4">
        @if (cartService.items().length === 0) {
          <div class="flex flex-col items-center justify-center h-full text-center py-12">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-16 w-16 text-muted-foreground/50 mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
            </svg>
            <h3 class="font-display text-lg font-semibold text-muted-foreground mb-2">Your cart is empty</h3>
            <p class="text-sm text-muted-foreground mb-6">Discover unique handcrafted treasures</p>
            <a 
              routerLink="/products" 
              (click)="cartService.closeCart()"
              class="btn-primary">
              Browse Products
            </a>
          </div>
        } @else {
          <div class="space-y-4">
            @for (item of cartService.items(); track item.product.id) {
              <div class="flex gap-4 p-3 bg-muted/50 rounded-lg animate-fade-in">
                <!-- Product Image -->
                <a 
                  [routerLink]="['/product', item.product.id]"
                  (click)="cartService.closeCart()"
                  class="flex-shrink-0">
                  <img 
                    [src]="item.product.images[0]" 
                    [alt]="item.product.name"
                    class="w-20 h-20 object-cover rounded-md"
                  />
                </a>
                
                <!-- Product Info -->
                <div class="flex-1 min-w-0">
                  <a 
                    [routerLink]="['/product', item.product.id]"
                    (click)="cartService.closeCart()"
                    class="font-medium text-foreground hover:text-primary line-clamp-2 text-sm">
                    {{ item.product.name }}
                  </a>
                  <p class="text-sm text-muted-foreground mt-1">
                    {{ item.product.artisan.name }}
                  </p>
                  <p class="text-primary font-semibold mt-1">
                    \${{ (item.product.price * item.quantity).toFixed(2) }}
                  </p>
                </div>

                <!-- Quantity Controls -->
                <div class="flex flex-col items-end justify-between">
                  <button 
                    (click)="cartService.removeItem(item.product.id)"
                    class="p-1 text-muted-foreground hover:text-destructive transition-colors"
                    aria-label="Remove item">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                  </button>
                  
                  <div class="flex items-center gap-2 bg-background rounded-md border border-border">
                    <button 
                      (click)="cartService.updateQuantity(item.product.id, item.quantity - 1)"
                      class="w-8 h-8 flex items-center justify-center hover:bg-muted rounded-l-md transition-colors"
                      aria-label="Decrease quantity">
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
                      </svg>
                    </button>
                    <span class="w-8 text-center text-sm font-medium">{{ item.quantity }}</span>
                    <button 
                      (click)="cartService.updateQuantity(item.product.id, item.quantity + 1)"
                      class="w-8 h-8 flex items-center justify-center hover:bg-muted rounded-r-md transition-colors"
                      aria-label="Increase quantity">
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                      </svg>
                    </button>
                  </div>
                </div>
              </div>
            }
          </div>
        }
      </div>

      <!-- Footer -->
      @if (cartService.items().length > 0) {
        <div class="border-t border-border p-4 space-y-4">
          <!-- Subtotal -->
          <div class="flex justify-between items-center">
            <span class="text-muted-foreground">Subtotal</span>
            <span class="font-semibold text-lg">\${{ cartService.totalPrice().toFixed(2) }}</span>
          </div>
          
          <p class="text-xs text-muted-foreground text-center">
            Shipping and taxes calculated at checkout
          </p>

          <!-- Checkout Button -->
          <button class="btn-primary w-full">
            Proceed to Checkout
          </button>

          <!-- Continue Shopping -->
          <button 
            (click)="cartService.closeCart()"
            class="btn-ghost w-full text-sm">
            Continue Shopping
          </button>

          <!-- Clear Cart -->
          <button 
            (click)="cartService.clearCart()"
            class="text-xs text-muted-foreground hover:text-destructive transition-colors w-full text-center">
            Clear Cart
          </button>
        </div>
      }
    </aside>
  `,
    styles: [`
    :host {
      display: contents;
    }
    
    .drawer {
      display: flex;
      flex-direction: column;
    }
    
    .line-clamp-2 {
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }
  `]
})
export class CartDrawerComponent {
    cartService = inject(CartService);

    @HostListener('document:keydown.escape')
    onEscapeKey(): void {
        this.cartService.closeCart();
    }
}
