import { Component, Input, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Product } from '../../models/product.model';
import { CartService } from '../../../core/services/cart.service';

@Component({
    selector: 'app-product-card',
    standalone: true,
    imports: [CommonModule, RouterLink],
    template: `
    <article class="card-hover group overflow-hidden">
      <!-- Image Container -->
      <a [routerLink]="['/product', product.id]" class="block relative aspect-square overflow-hidden">
        <img 
          [src]="product.images[0]" 
          [alt]="product.name"
          loading="lazy"
          class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110"
        />
        
        <!-- Badges -->
        <div class="absolute top-3 left-3 flex flex-col gap-2">
          @if (product.featured) {
            <span class="badge-primary">Featured</span>
          }
          @if (product.originalPrice && product.originalPrice > product.price) {
            <span class="badge bg-destructive text-destructive-foreground">
              -{{ getDiscountPercent() }}%
            </span>
          }
        </div>

        <!-- Quick Add Button -->
        <button 
          (click)="addToCart($event)"
          class="absolute bottom-3 right-3 w-10 h-10 bg-primary text-primary-foreground rounded-full 
                 flex items-center justify-center shadow-lg opacity-0 translate-y-4
                 group-hover:opacity-100 group-hover:translate-y-0 transition-all duration-300
                 hover:bg-primary/90"
          aria-label="Add to cart">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
          </svg>
        </button>
      </a>

      <!-- Content -->
      <div class="p-4 space-y-3">
        <!-- Category & Artisan -->
        <div class="flex items-center justify-between text-xs text-muted-foreground">
          <span class="uppercase tracking-wide">{{ product.subcategory }}</span>
          <div class="flex items-center gap-1">
            <img 
              [src]="product.artisan.avatar" 
              [alt]="product.artisan.name"
              class="w-5 h-5 rounded-full object-cover"
            />
            <span class="truncate max-w-[80px]">{{ product.artisan.name }}</span>
          </div>
        </div>

        <!-- Title -->
        <h3 class="font-display text-lg font-semibold text-foreground line-clamp-2 leading-tight">
          <a [routerLink]="['/product', product.id]" class="hover:text-primary transition-colors">
            {{ product.name }}
          </a>
        </h3>

        <!-- Rating -->
        <div class="flex items-center gap-2">
          <div class="flex items-center">
            @for (star of [1,2,3,4,5]; track star) {
              <svg 
                class="w-4 h-4" 
                [class.star-filled]="star <= Math.round(product.rating)"
                [class.star-empty]="star > Math.round(product.rating)"
                fill="currentColor" 
                viewBox="0 0 20 20">
                <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/>
              </svg>
            }
          </div>
          <span class="text-sm text-muted-foreground">({{ product.reviewCount }})</span>
        </div>

        <!-- Price -->
        <div class="flex items-center gap-2">
          <span class="text-xl font-bold text-primary">
            \${{ product.price.toFixed(2) }}
          </span>
          @if (product.originalPrice && product.originalPrice > product.price) {
            <span class="text-sm text-muted-foreground line-through">
              \${{ product.originalPrice.toFixed(2) }}
            </span>
          }
        </div>

        <!-- Stock Status -->
        @if (!product.inStock) {
          <p class="text-sm text-destructive font-medium">Out of Stock</p>
        }
      </div>
    </article>
  `,
    styles: [`
    .line-clamp-2 {
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }
  `]
})
export class ProductCardComponent {
    @Input({ required: true }) product!: Product;

    private cartService = inject(CartService);
    Math = Math;

    getDiscountPercent(): number {
        if (!this.product.originalPrice) return 0;
        return Math.round(((this.product.originalPrice - this.product.price) / this.product.originalPrice) * 100);
    }

    addToCart(event: Event): void {
        event.preventDefault();
        event.stopPropagation();
        if (this.product.inStock) {
            this.cartService.addItem(this.product);
        }
    }
}
