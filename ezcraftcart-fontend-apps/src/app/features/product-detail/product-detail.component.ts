import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ProductCardComponent } from '../../shared/components/product-card/product-card.component';
import { getProductById, products } from '../../shared/data/products.data';
import { Product } from '../../shared/models/product.model';
import { CartService } from '../../core/services/cart.service';

@Component({
    selector: 'app-product-detail',
    standalone: true,
    imports: [CommonModule, RouterLink, ProductCardComponent],
    template: `
    @if (product) {
      <main class="py-8">
        <div class="container-custom">
          <!-- Breadcrumb -->
          <nav class="mb-6">
            <ol class="flex items-center gap-2 text-sm flex-wrap">
              <li><a routerLink="/" class="text-muted-foreground hover:text-primary">Home</a></li>
              <li class="text-muted-foreground">/</li>
              <li><a routerLink="/products" class="text-muted-foreground hover:text-primary">Products</a></li>
              <li class="text-muted-foreground">/</li>
              <li><a [routerLink]="['/products']" [queryParams]="{category: product.category}" class="text-muted-foreground hover:text-primary capitalize">{{ product.category }}</a></li>
              <li class="text-muted-foreground">/</li>
              <li class="text-foreground font-medium truncate max-w-[200px]">{{ product.name }}</li>
            </ol>
          </nav>

          <!-- Product Details -->
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 lg:gap-12">
            <!-- Image Gallery -->
            <div class="space-y-4">
              <div class="aspect-square overflow-hidden rounded-xl bg-muted">
                <img 
                  [src]="selectedImage" 
                  [alt]="product.name"
                  class="w-full h-full object-cover"
                />
              </div>
              
              @if (product.images.length > 1) {
                <div class="flex gap-3 overflow-x-auto pb-2">
                  @for (image of product.images; track image) {
                    <button 
                      (click)="selectedImage = image"
                      class="flex-shrink-0 w-20 h-20 rounded-lg overflow-hidden border-2 transition-colors"
                      [class.border-primary]="selectedImage === image"
                      [class.border-transparent]="selectedImage !== image">
                      <img [src]="image" [alt]="product.name" class="w-full h-full object-cover" />
                    </button>
                  }
                </div>
              }
            </div>

            <!-- Product Info -->
            <div class="space-y-6">
              <!-- Tags -->
              <div class="flex flex-wrap gap-2">
                @if (product.featured) {
                  <span class="badge-primary">Featured</span>
                }
                @if (product.trending) {
                  <span class="badge-accent">Trending</span>
                }
                @if (!product.inStock) {
                  <span class="badge bg-destructive/10 text-destructive">Out of Stock</span>
                }
              </div>

              <!-- Title -->
              <div>
                <p class="text-sm text-muted-foreground uppercase tracking-wide mb-2">{{ product.subcategory }}</p>
                <h1 class="font-display text-3xl md:text-4xl font-bold text-foreground">{{ product.name }}</h1>
              </div>

              <!-- Rating -->
              <div class="flex items-center gap-3">
                <div class="flex items-center">
                  @for (star of [1,2,3,4,5]; track star) {
                    <svg 
                      class="w-5 h-5" 
                      [class.star-filled]="star <= Math.round(product.rating)"
                      [class.star-empty]="star > Math.round(product.rating)"
                      fill="currentColor" 
                      viewBox="0 0 20 20">
                      <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/>
                    </svg>
                  }
                </div>
                <span class="text-foreground font-medium">{{ product.rating }}</span>
                <span class="text-muted-foreground">({{ product.reviewCount }} reviews)</span>
              </div>

              <!-- Price -->
              <div class="flex items-end gap-3">
                <span class="text-3xl font-bold text-primary">\${{ product.price.toFixed(2) }}</span>
                @if (product.originalPrice && product.originalPrice > product.price) {
                  <span class="text-lg text-muted-foreground line-through">\${{ product.originalPrice.toFixed(2) }}</span>
                  <span class="badge bg-destructive text-destructive-foreground">
                    Save {{ getDiscountPercent() }}%
                  </span>
                }
              </div>

              <!-- Description -->
              <p class="text-muted-foreground leading-relaxed">{{ product.description }}</p>

              <!-- Artisan Info -->
              <div class="flex items-center gap-4 p-4 bg-muted/50 rounded-lg">
                <img 
                  [src]="product.artisan.avatar" 
                  [alt]="product.artisan.name"
                  class="w-12 h-12 rounded-full object-cover"
                />
                <div>
                  <p class="font-medium text-foreground">{{ product.artisan.name }}</p>
                  <p class="text-sm text-muted-foreground flex items-center gap-1">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                    </svg>
                    {{ product.artisan.location }}
                  </p>
                </div>
              </div>

              <!-- Quantity & Add to Cart -->
              <div class="flex flex-col sm:flex-row gap-4">
                <div class="flex items-center border border-border rounded-md">
                  <button 
                    (click)="decreaseQuantity()"
                    class="w-12 h-12 flex items-center justify-center hover:bg-muted transition-colors"
                    [disabled]="quantity <= 1">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
                    </svg>
                  </button>
                  <span class="w-12 text-center font-medium">{{ quantity }}</span>
                  <button 
                    (click)="increaseQuantity()"
                    class="w-12 h-12 flex items-center justify-center hover:bg-muted transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                    </svg>
                  </button>
                </div>
                
                <button 
                  (click)="addToCart()"
                  [disabled]="!product.inStock"
                  class="btn-primary flex-1 sm:flex-none"
                  [class.opacity-50]="!product.inStock"
                  [class.cursor-not-allowed]="!product.inStock">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
                  </svg>
                  {{ product.inStock ? 'Add to Cart' : 'Out of Stock' }}
                </button>
              </div>

              <!-- Tags -->
              <div class="flex flex-wrap gap-2">
                @for (tag of product.tags; track tag) {
                  <a 
                    [routerLink]="['/products']"
                    class="px-3 py-1 bg-muted text-muted-foreground text-sm rounded-full hover:bg-muted/80 transition-colors">
                    #{{ tag }}
                  </a>
                }
              </div>
            </div>
          </div>

          <!-- Related Products -->
          <section class="mt-16">
            <h2 class="font-display text-2xl font-bold text-foreground mb-8">You May Also Like</h2>
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              @for (relatedProduct of relatedProducts; track relatedProduct.id) {
                <app-product-card [product]="relatedProduct" />
              }
            </div>
          </section>
        </div>
      </main>
    } @else {
      <!-- Loading State -->
      <div class="flex items-center justify-center min-h-[60vh]">
        <div class="h-8 w-8 animate-spin rounded-full border-4 border-primary border-t-transparent"></div>
      </div>
    }
  `,
    styles: ``
})
export class ProductDetailComponent implements OnInit {
    private route = inject(ActivatedRoute);
    private cartService = inject(CartService);

    product: Product | undefined;
    relatedProducts: Product[] = [];
    selectedImage = '';
    quantity = 1;
    Math = Math;

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            const id = params['id'];
            this.product = getProductById(id);

            if (this.product) {
                this.selectedImage = this.product.images[0];
                this.relatedProducts = products
                    .filter(p => p.category === this.product?.category && p.id !== this.product?.id)
                    .slice(0, 4);
            }
        });
    }

    getDiscountPercent(): number {
        if (!this.product?.originalPrice) return 0;
        return Math.round(((this.product.originalPrice - this.product.price) / this.product.originalPrice) * 100);
    }

    increaseQuantity(): void {
        this.quantity++;
    }

    decreaseQuantity(): void {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }

    addToCart(): void {
        if (this.product && this.product.inStock) {
            this.cartService.addItem(this.product, this.quantity);
            this.quantity = 1;
        }
    }
}
