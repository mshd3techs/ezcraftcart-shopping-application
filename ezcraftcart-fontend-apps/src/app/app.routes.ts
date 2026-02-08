import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadComponent: () => import('./features/home/home.component').then(m => m.HomeComponent),
        title: 'EzCraftCart - Handcrafted Artisan Marketplace'
    },
    {
        path: 'products',
        loadComponent: () => import('./features/products/products.component').then(m => m.ProductsComponent),
        title: 'Products | EzCraftCart'
    },
    {
        path: 'product/:id',
        loadComponent: () => import('./features/product-detail/product-detail.component').then(m => m.ProductDetailComponent),
        title: 'Product Details | EzCraftCart'
    },
    {
        path: 'login',
        loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent),
        title: 'Sign In | EzCraftCart'
    },
    {
        path: 'register',
        loadComponent: () => import('./features/auth/register/register.component').then(m => m.RegisterComponent),
        title: 'Create Account | EzCraftCart'
    },
    {
        path: '**',
        loadComponent: () => import('./features/not-found/not-found.component').then(m => m.NotFoundComponent),
        title: 'Page Not Found | EzCraftCart'
    }
];
