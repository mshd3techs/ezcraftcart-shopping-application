export interface Product {
    id: string;
    name: string;
    description: string;
    price: number;
    originalPrice?: number;
    category: 'ceramic' | 'wooden' | 'earth' | 'scented-oils' | 'metal' | 'pots' | 'perfumes';
    subcategory: string;
    images: string[];
    rating: number;
    reviewCount: number;
    artisan: {
        name: string;
        location: string;
        avatar: string;
    };
    tags: string[];
    inStock: boolean;
    featured?: boolean;
    trending?: boolean;
}

export interface Category {
    id: string;
    name: string;
    slug: string;
    description: string;
    image: string;
    productCount: number;
}

export interface CartItem {
    product: Product;
    quantity: number;
}
