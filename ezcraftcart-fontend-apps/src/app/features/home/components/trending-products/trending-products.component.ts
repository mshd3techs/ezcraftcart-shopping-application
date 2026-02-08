import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProductCardComponent } from '../../../../shared/components/product-card/product-card.component';
import { getTrendingProducts } from '../../../../shared/data/products.data';

@Component({
    selector: 'app-trending-products',
    standalone: true,
    imports: [CommonModule, RouterLink, ProductCardComponent],
    template: `
    <section class="py-16 bg-muted/50 artisan-pattern">
      <div class="container-custom">
        <!-- Section Header -->
        <div class="text-center mb-10">
          <span class="text-secondary font-medium text-sm uppercase tracking-wider">What's Hot</span>
          <h2 class="font-display text-3xl md:text-4xl font-bold text-foreground mt-2">
            Trending Now
          </h2>
          <p class="text-muted-foreground mt-4 max-w-2xl mx-auto">
            The most sought-after items this season, loved by artisan enthusiasts everywhere
          </p>
        </div>

        <!-- Products Grid -->
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          @for (product of trendingProducts; track product.id) {
            <app-product-card [product]="product" />
          }
        </div>

        <!-- CTA -->
        <div class="text-center mt-10">
          <a routerLink="/products" class="btn-secondary">
            Explore All Trending
            <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 ml-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7l5 5m0 0l-5 5m5-5H6" />
            </svg>
          </a>
        </div>
      </div>
    </section>
  `,
    styles: ``
})
export class TrendingProductsComponent {
    trendingProducts = getTrendingProducts();
}
