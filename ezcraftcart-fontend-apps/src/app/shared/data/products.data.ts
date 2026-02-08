import { Category, Product } from '../models/product.model';

export const categories: Category[] = [
    {
        id: '1',
        name: 'Ceramic Crafts',
        slug: 'ceramic',
        description: 'Handcrafted pottery and ceramic artworks',
        image: 'https://images.unsplash.com/photo-1565193566173-7a0ee3dbe261?w=600&h=400&fit=crop',
        productCount: 156,
    },
    {
        id: '2',
        name: 'Wooden Crafts',
        slug: 'wooden',
        description: 'Artisan woodwork and carved treasures',
        image: 'https://images.unsplash.com/photo-1605627079912-97c3810a11a4?w=600&h=400&fit=crop',
        productCount: 198,
    },
    {
        id: '3',
        name: 'Earth Crafts',
        slug: 'earth',
        description: 'Natural clay and terracotta creations',
        image: 'https://images.unsplash.com/photo-1493106641515-6b5631de4bb9?w=600&h=400&fit=crop',
        productCount: 124,
    },
    {
        id: '4',
        name: 'Scented Oils',
        slug: 'scented-oils',
        description: 'Aromatic essential oils and fragrances',
        image: 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=600&h=400&fit=crop',
        productCount: 87,
    },
    {
        id: '5',
        name: 'Metal Crafts',
        slug: 'metal',
        description: 'Forged and sculpted metalwork art',
        image: 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600&h=400&fit=crop',
        productCount: 145,
    },
    {
        id: '6',
        name: 'Pots & Planters',
        slug: 'pots',
        description: 'Decorative pots and garden planters',
        image: 'https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=600&h=400&fit=crop',
        productCount: 112,
    },
];

export const products: Product[] = [
    {
        id: 'p1',
        name: 'Hand-Painted Ceramic Vase Set',
        description: 'Set of 3 beautifully hand-painted ceramic vases with traditional Asian floral motifs. Each piece is unique and signed by the artist.',
        price: 145.00,
        category: 'ceramic',
        subcategory: 'Vases',
        images: [
            'https://images.unsplash.com/photo-1578749556568-bc2c40e68b61?w=600&h=600&fit=crop',
            'https://images.unsplash.com/photo-1565193566173-7a0ee3dbe261?w=600&h=600&fit=crop',
        ],
        rating: 4.9,
        reviewCount: 87,
        artisan: {
            name: 'Chen Ceramics',
            location: 'Jingdezhen, China',
            avatar: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100&h=100&fit=crop',
        },
        tags: ['ceramic', 'vase', 'hand-painted', 'floral'],
        inStock: true,
        featured: true,
        trending: true,
    },
    {
        id: 'p2',
        name: 'Celadon Tea Cup Set',
        description: 'Traditional celadon glazed tea cups with subtle jade-green finish. Set of 6 cups with matching tray.',
        price: 89.00,
        category: 'ceramic',
        subcategory: 'Tea Sets',
        images: [
            'https://images.unsplash.com/photo-1544787219-7f47ccb76574?w=600&h=600&fit=crop',
        ],
        rating: 4.8,
        reviewCount: 124,
        artisan: {
            name: 'Longquan Pottery',
            location: 'Zhejiang, China',
            avatar: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=100&h=100&fit=crop',
        },
        tags: ['celadon', 'tea', 'cups', 'traditional'],
        inStock: true,
        trending: true,
    },
    {
        id: 'p3',
        name: 'Raku-Fired Decorative Bowl',
        description: 'Unique raku-fired ceramic bowl with copper and turquoise glaze effects. A stunning centerpiece for any room.',
        price: 175.00,
        category: 'ceramic',
        subcategory: 'Bowls',
        images: [
            'https://images.unsplash.com/photo-1610701596007-11502861dcfa?w=600&h=600&fit=crop',
        ],
        rating: 5.0,
        reviewCount: 45,
        artisan: {
            name: 'Kyoto Raku Studio',
            location: 'Kyoto, Japan',
            avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100&h=100&fit=crop',
        },
        tags: ['raku', 'bowl', 'decorative', 'japanese'],
        inStock: true,
        featured: true,
    },
    {
        id: 'p4',
        name: 'Hand-Carved Wooden Bowl',
        description: 'Exquisitely hand-carved wooden bowl made from sustainable teak. Perfect for serving or display.',
        price: 95.00,
        originalPrice: 120.00,
        category: 'wooden',
        subcategory: 'Bowls',
        images: [
            'https://images.unsplash.com/photo-1605627079912-97c3810a11a4?w=600&h=600&fit=crop',
        ],
        rating: 4.7,
        reviewCount: 156,
        artisan: {
            name: 'Bali Woodworks',
            location: 'Ubud, Indonesia',
            avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=100&h=100&fit=crop',
        },
        tags: ['wooden', 'bowl', 'teak', 'handcarved'],
        inStock: true,
        featured: true,
        trending: true,
    },
    {
        id: 'p5',
        name: 'Artisan Wooden Jewelry Box',
        description: 'Intricately designed wooden jewelry box with hand-carved floral patterns and velvet interior.',
        price: 185.00,
        category: 'wooden',
        subcategory: 'Storage',
        images: [
            'https://images.unsplash.com/photo-1586864387967-d02ef85d93e8?w=600&h=600&fit=crop',
        ],
        rating: 4.9,
        reviewCount: 78,
        artisan: {
            name: 'Kashmir Crafts',
            location: 'Srinagar, India',
            avatar: 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=100&h=100&fit=crop',
        },
        tags: ['wooden', 'jewelry', 'box', 'handcarved'],
        inStock: true,
        featured: true,
    },
    {
        id: 'p6',
        name: 'Terracotta Garden Planter',
        description: 'Large handmade terracotta planter with traditional geometric patterns. Weather-resistant and durable.',
        price: 65.00,
        category: 'earth',
        subcategory: 'Planters',
        images: [
            'https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=600&h=600&fit=crop',
        ],
        rating: 4.6,
        reviewCount: 203,
        artisan: {
            name: 'Rajasthani Pottery',
            location: 'Jaipur, India',
            avatar: 'https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=100&h=100&fit=crop',
        },
        tags: ['terracotta', 'planter', 'garden', 'handmade'],
        inStock: true,
        trending: true,
    },
    {
        id: 'p7',
        name: 'Lavender Essential Oil Set',
        description: 'Pure lavender essential oil gift set with ceramic diffuser. Calming aromatherapy for home or office.',
        price: 55.00,
        category: 'scented-oils',
        subcategory: 'Essential Oils',
        images: [
            'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=600&h=600&fit=crop',
        ],
        rating: 4.8,
        reviewCount: 312,
        artisan: {
            name: 'Provence Aromatics',
            location: 'Grasse, France',
            avatar: 'https://images.unsplash.com/photo-1580489944761-15a19d654956?w=100&h=100&fit=crop',
        },
        tags: ['essential-oil', 'lavender', 'aromatherapy', 'gift-set'],
        inStock: true,
        featured: true,
    },
    {
        id: 'p8',
        name: 'Hand-Forged Iron Candle Holder',
        description: 'Rustic hand-forged iron candle holder with intricate scrollwork. Holds 3 pillar candles.',
        price: 125.00,
        category: 'metal',
        subcategory: 'Candle Holders',
        images: [
            'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=600&h=600&fit=crop',
        ],
        rating: 4.7,
        reviewCount: 89,
        artisan: {
            name: 'Blacksmith Heritage',
            location: 'Yorkshire, UK',
            avatar: 'https://images.unsplash.com/photo-1463453091185-61582044d556?w=100&h=100&fit=crop',
        },
        tags: ['iron', 'candle-holder', 'forged', 'rustic'],
        inStock: true,
        featured: true,
        trending: true,
    },
    {
        id: 'p9',
        name: 'Decorative Copper Wall Art',
        description: 'Stunning copper wall art piece featuring abstract geometric design. Hand-hammered finish.',
        price: 295.00,
        category: 'metal',
        subcategory: 'Wall Art',
        images: [
            'https://images.unsplash.com/photo-1513519245088-0e12902e5a38?w=600&h=600&fit=crop',
        ],
        rating: 4.9,
        reviewCount: 34,
        artisan: {
            name: 'Copper Arts Studio',
            location: 'Santa Fe, USA',
            avatar: 'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=100&h=100&fit=crop',
        },
        tags: ['copper', 'wall-art', 'geometric', 'handmade'],
        inStock: true,
    },
    {
        id: 'p10',
        name: 'Glazed Ceramic Pot Collection',
        description: 'Set of 3 glazed ceramic pots in varying sizes with beautiful ombre effect. Perfect for succulents.',
        price: 78.00,
        category: 'pots',
        subcategory: 'Indoor Pots',
        images: [
            'https://images.unsplash.com/photo-1509423350716-97f9360b4e09?w=600&h=600&fit=crop',
        ],
        rating: 4.5,
        reviewCount: 178,
        artisan: {
            name: 'Modern Ceramics Co',
            location: 'Portland, USA',
            avatar: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=100&h=100&fit=crop',
        },
        tags: ['ceramic', 'pot', 'glazed', 'succulent'],
        inStock: true,
        trending: true,
    },
    {
        id: 'p11',
        name: 'Sandalwood Incense Gift Box',
        description: 'Premium sandalwood incense sticks with hand-carved wooden holder. Traditional Indian craftsmanship.',
        price: 42.00,
        category: 'scented-oils',
        subcategory: 'Incense',
        images: [
            'https://images.unsplash.com/photo-1541849546-216549ae216d?w=600&h=600&fit=crop',
        ],
        rating: 4.6,
        reviewCount: 245,
        artisan: {
            name: 'Mysore Fragrances',
            location: 'Mysore, India',
            avatar: 'https://images.unsplash.com/photo-1552058544-f2b08422138a?w=100&h=100&fit=crop',
        },
        tags: ['incense', 'sandalwood', 'gift', 'traditional'],
        inStock: true,
    },
    {
        id: 'p12',
        name: 'Bamboo Wind Chimes',
        description: 'Melodious bamboo wind chimes with coconut shell top. Creates soothing natural sounds.',
        price: 35.00,
        category: 'wooden',
        subcategory: 'Decor',
        images: [
            'https://images.unsplash.com/photo-1509099652299-30938b0aeb63?w=600&h=600&fit=crop',
        ],
        rating: 4.4,
        reviewCount: 432,
        artisan: {
            name: 'Bali Sounds',
            location: 'Bali, Indonesia',
            avatar: 'https://images.unsplash.com/photo-1489424731084-a5d8b219a5bb?w=100&h=100&fit=crop',
        },
        tags: ['bamboo', 'wind-chimes', 'garden', 'sound'],
        inStock: true,
        trending: true,
    },
];

export const getFeaturedProducts = (): Product[] => {
    return products.filter(p => p.featured);
};

export const getTrendingProducts = (): Product[] => {
    return products.filter(p => p.trending);
};

export const getProductsByCategory = (category: string): Product[] => {
    return products.filter(p => p.category === category);
};

export const getProductById = (id: string): Product | undefined => {
    return products.find(p => p.id === id);
};
