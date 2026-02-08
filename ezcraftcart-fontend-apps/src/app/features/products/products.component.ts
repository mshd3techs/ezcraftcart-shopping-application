import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductCardComponent } from '../../shared/components/product-card/product-card.component';
import { products, categories } from '../../shared/data/products.data';
import { Product } from '../../shared/models/product.model';

@Component({
    selector: 'app-products',
    standalone: true,
    imports: [CommonModule, RouterLink, FormsModule, ProductCardComponent],
    template: `
    <main class="py-8">
      <div class="container-custom">
        <!-- Breadcrumb -->
        <nav class="mb-6">
          <ol class="flex items-center gap-2 text-sm">
            <li><a routerLink="/" class="text-muted-foreground hover:text-primary">Home</a></li>
            <li class="text-muted-foreground">/</li>
            <li class="text-foreground font-medium">Products</li>
            @if (selectedCategory) {
              <li class="text-muted-foreground">/</li>
              <li class="text-foreground font-medium capitalize">{{ selectedCategory }}</li>
            }
          </ol>
        </nav>

        <!-- Page Header -->
        <div class="mb-8">
          <h1 class="font-display text-3xl md:text-4xl font-bold text-foreground">
            @if (selectedCategory) {
              {{ getCategoryName(selectedCategory) }}
            } @else {
              All Products
            }
          </h1>
          <p class="text-muted-foreground mt-2">
            Showing {{ filteredProducts.length }} products
          </p>
        </div>

        <div class="flex flex-col lg:flex-row gap-8">
          <!-- Sidebar Filters -->
          <aside class="w-full lg:w-64 flex-shrink-0">
            <div class="card p-6 sticky top-24">
              <h2 class="font-display text-lg font-semibold mb-4">Filters</h2>
              
              <!-- Categories -->
              <div class="mb-6">
                <h3 class="font-medium text-sm text-muted-foreground uppercase tracking-wide mb-3">
                  Categories
                </h3>
                <div class="space-y-2">
                  <label class="flex items-center gap-2 cursor-pointer">
                    <input 
                      type="radio" 
                      name="category" 
                      [value]="''"
                      [(ngModel)]="selectedCategory"
                      (ngModelChange)="filterProducts()"
                      class="w-4 h-4 text-primary"
                    />
                    <span class="text-sm">All Categories</span>
                  </label>
                  @for (category of categories; track category.id) {
                    <label class="flex items-center gap-2 cursor-pointer">
                      <input 
                        type="radio" 
                        name="category" 
                        [value]="category.slug"
                        [(ngModel)]="selectedCategory"
                        (ngModelChange)="filterProducts()"
                        class="w-4 h-4 text-primary"
                      />
                      <span class="text-sm">{{ category.name }}</span>
                      <span class="text-xs text-muted-foreground ml-auto">({{ category.productCount }})</span>
                    </label>
                  }
                </div>
              </div>

              <!-- Price Range -->
              <div class="mb-6">
                <h3 class="font-medium text-sm text-muted-foreground uppercase tracking-wide mb-3">
                  Price Range
                </h3>
                <div class="flex gap-2">
                  <input 
                    type="number" 
                    placeholder="Min" 
                    [(ngModel)]="minPrice"
                    (ngModelChange)="filterProducts()"
                    class="input text-sm py-2 w-full"
                  />
                  <input 
                    type="number" 
                    placeholder="Max" 
                    [(ngModel)]="maxPrice"
                    (ngModelChange)="filterProducts()"
                    class="input text-sm py-2 w-full"
                  />
                </div>
              </div>

              <!-- Availability -->
              <div class="mb-6">
                <h3 class="font-medium text-sm text-muted-foreground uppercase tracking-wide mb-3">
                  Availability
                </h3>
                <label class="flex items-center gap-2 cursor-pointer">
                  <input 
                    type="checkbox" 
                    [(ngModel)]="inStockOnly"
                    (ngModelChange)="filterProducts()"
                    class="w-4 h-4 text-primary rounded"
                  />
                  <span class="text-sm">In Stock Only</span>
                </label>
              </div>

              <!-- Clear Filters -->
              <button 
                (click)="clearFilters()"
                class="btn-ghost w-full text-sm text-muted-foreground">
                Clear All Filters
              </button>
            </div>
          </aside>

          <!-- Products Grid -->
          <div class="flex-1">
            <!-- Sort & View Options -->
            <div class="flex flex-wrap items-center justify-between gap-4 mb-6">
              <div class="flex items-center gap-4">
                <select 
                  [(ngModel)]="sortBy" 
                  (ngModelChange)="sortProducts()"
                  class="input py-2 pr-10 text-sm w-auto">
                  <option value="featured">Featured</option>
                  <option value="price-low">Price: Low to High</option>
                  <option value="price-high">Price: High to Low</option>
                  <option value="rating">Highest Rated</option>
                  <option value="newest">Newest</option>
                </select>
              </div>

              <!-- Grid Toggle -->
              <div class="flex items-center gap-2">
                <button 
                  (click)="gridCols = 2"
                  class="p-2 rounded-md transition-colors"
                  [class.bg-muted]="gridCols === 2"
                  aria-label="2 columns">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6a2 2 0 012-2h4a2 2 0 012 2v4a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h4a2 2 0 012 2v4a2 2 0 01-2 2h-4a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h4a2 2 0 012 2v4a2 2 0 01-2 2H6a2 2 0 01-2-2v-4zM14 16a2 2 0 012-2h4a2 2 0 012 2v4a2 2 0 01-2 2h-4a2 2 0 01-2-2v-4z" />
                  </svg>
                </button>
                <button 
                  (click)="gridCols = 3"
                  class="p-2 rounded-md transition-colors"
                  [class.bg-muted]="gridCols === 3"
                  aria-label="3 columns">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z" />
                  </svg>
                </button>
              </div>
            </div>

            <!-- Products -->
            @if (filteredProducts.length > 0) {
              <div 
                class="grid gap-6"
                [ngClass]="{
                  'grid-cols-1 sm:grid-cols-2': gridCols === 2,
                  'grid-cols-1 sm:grid-cols-2 lg:grid-cols-3': gridCols === 3
                }">
                @for (product of filteredProducts; track product.id) {
                  <app-product-card [product]="product" />
                }
              </div>
            } @else {
              <div class="text-center py-16">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-16 w-16 mx-auto text-muted-foreground/50 mb-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                <h3 class="font-display text-xl font-semibold text-muted-foreground mb-2">No products found</h3>
                <p class="text-muted-foreground mb-6">Try adjusting your filters or search criteria</p>
                <button (click)="clearFilters()" class="btn-primary">Clear Filters</button>
              </div>
            }
          </div>
        </div>
      </div>
    </main>
  `,
    styles: ``
})
export class ProductsComponent implements OnInit {
    private route = inject(ActivatedRoute);

    allProducts = products;
    filteredProducts: Product[] = [];
    categories = categories;

    selectedCategory = '';
    minPrice: number | null = null;
    maxPrice: number | null = null;
    inStockOnly = false;
    sortBy = 'featured';
    gridCols = 3;

    ngOnInit(): void {
        this.route.queryParams.subscribe(params => {
            if (params['category']) {
                this.selectedCategory = params['category'];
            }
            this.filterProducts();
        });
    }

    filterProducts(): void {
        let filtered = [...this.allProducts];

        // Category filter
        if (this.selectedCategory) {
            filtered = filtered.filter(p => p.category === this.selectedCategory);
        }

        // Price filter
        if (this.minPrice !== null) {
            filtered = filtered.filter(p => p.price >= this.minPrice!);
        }
        if (this.maxPrice !== null) {
            filtered = filtered.filter(p => p.price <= this.maxPrice!);
        }

        // Stock filter
        if (this.inStockOnly) {
            filtered = filtered.filter(p => p.inStock);
        }

        this.filteredProducts = filtered;
        this.sortProducts();
    }

    sortProducts(): void {
        switch (this.sortBy) {
            case 'price-low':
                this.filteredProducts.sort((a, b) => a.price - b.price);
                break;
            case 'price-high':
                this.filteredProducts.sort((a, b) => b.price - a.price);
                break;
            case 'rating':
                this.filteredProducts.sort((a, b) => b.rating - a.rating);
                break;
            case 'featured':
            default:
                this.filteredProducts.sort((a, b) => (b.featured ? 1 : 0) - (a.featured ? 1 : 0));
        }
    }

    clearFilters(): void {
        this.selectedCategory = '';
        this.minPrice = null;
        this.maxPrice = null;
        this.inStockOnly = false;
        this.sortBy = 'featured';
        this.filterProducts();
    }

    getCategoryName(slug: string): string {
        const category = this.categories.find(c => c.slug === slug);
        return category?.name || slug;
    }
}
