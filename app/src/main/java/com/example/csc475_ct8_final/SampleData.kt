package com.example.csc475_ct8_final

object SampleData {
    val recipes = listOf(
        Recipe(
            id = 1001,
            title = "Classic Cheeseburger",
            description = "Juicy beef patty with melted cheddar cheese on a toasted bun.",
            ingredients = listOf("1 lb 80/20 ground beef", "4 slices American cheese", "4 burger buns", "Salt", "Black pepper", "Butter", "Lettuce leaves", "Tomato slices", "Red onion", "Pickles", "Ketchup", "Mustard", "Mayonnaise"),
            instructions = "1. Divide beef into 4 equal portions. Form into patties about ½-inch thick and slightly wider than the buns. Press a thumbprint in the center of each to prevent shrinking.\n2. Generously season both sides with salt and pepper just before cooking.\n3. Heat a skillet or grill to medium-high. Cook for 3–4 minutes on one side until a crust forms. Flip.\n4. Immediately place a cheese slice on each patty. Cook for another 2–3 minutes (or until the desired doneness is reached).\n5. Butter the buns and toast them on the grill or in a pan for 1 minute.\n6. Spread mayo/ketchup on the bottom bun, add the patty, then layer lettuce, tomato, onion, and pickles.",
            cookingTime = "20 mins"
        ),
        Recipe(
            id = 1002,
            title = "Ground Beef Tacos",
            description = "Traditional Mexican-style tacos with seasoned beef and fresh toppings.",
            ingredients = listOf("1 lb ground beef", "1 small onion (diced)", "2 cloves garlic (minced)", "1 tbsp chili powder", "1 tsp cumin", "½ tsp paprika", "½ cup tomato sauce", "Salt", "Taco shells or tortillas", "Shredded cheese", "Shredded lettuce", "Salsa", "Sour cream"),
            instructions = "1. Sauté the beef and onion in a large skillet over medium-high heat until the beef is browned. Drain excess fat.\n2. Add garlic, chili powder, cumin, paprika, and salt. Cook for 1 minute to let spices bloom.\n3. Stir in the tomato sauce and a splash of water. Reduce heat to low and simmer for 5 minutes until thickened.\n4. Fill shells or warmed tortillas with meat and top with shredded cheese, lettuce, salsa, and sour cream.",
            cookingTime = "15 mins"
        ),
        Recipe(
            id = 1003,
            title = "Homestyle Chicken Soup",
            description = "Comforting soup with tender chicken and fresh vegetables.",
            ingredients = listOf("1 whole chicken (4 lbs)", "3 carrots (chopped)", "3 celery stalks (chopped)", "1 large onion (chopped)", "4 garlic cloves", "Fresh parsley", "Thyme", "1 bay leaf", "Salt", "Peppercorns", "Egg noodles"),
            instructions = "1. Place the whole chicken in a large stockpot. Add water to cover (about 10–12 cups). Add salt, peppercorns, and the bay leaf.\n2. Bring to a boil, then reduce to a low simmer. Skim off any foam. Cook for 45–60 minutes until the chicken is tender.\n3. Remove the chicken. Strain the broth and return it to the pot. Once the chicken is cool, shred the meat and discard the skin/bones.\n4. Add carrots, celery, and onion to the broth. Simmer for 15 minutes.\n5. If using noodles, cook them separately or in the broth until tender.\n6. Stir the shredded meat and fresh herbs back into the soup. Season with extra salt and pepper to taste.",
            cookingTime = "45 mins"
        ),
        Recipe(
            id = 1004,
            title = "Chicken and Dumplings",
            description = "Creamy chicken stew with fluffy homemade dumplings.",
            ingredients = listOf("2 cups shredded cooked chicken", "4 cups chicken broth", "1 cup milk", "1 ½ cups all-purpose flour", "1 tsp baking powder", "2 tbsp butter (melted)", "2 carrots (diced)", "2 celery stalks (diced)", "Salt", "Black pepper"),
            instructions = "1. Sauté diced carrots and celery in butter until soft. Stir in broth and bring to a simmer. Add the cooked chicken.\n2. In a bowl, whisk 1 cup flour, 1 tsp baking powder, and a pinch of salt. Stir in ½ cup milk and 2 tbsp melted butter until a sticky dough forms.\n3. Using a spoon, drop small portions of dough into the simmering soup.\n4. Cover the pot tightly and simmer on low for 12–15 minutes without lifting the lid. The dumplings should be fluffy and cooked through.",
            cookingTime = "60 mins"
        ),
        Recipe(
            id = 1005,
            title = "Spaghetti Carbonara",
            description = "Classic Italian pasta dish with eggs, cheese, and pancetta.",
            ingredients = listOf("12 oz spaghetti", "4 oz guanciale or pancetta (diced)", "3 large egg yolks", "1 whole egg", "1 cup Pecorino Romano (grated)", "Freshly cracked black pepper", "Salt (for pasta water)"),
            instructions = "1. Cook spaghetti in salted water until al dente. Reserve 1 cup of pasta water before draining.\n2. Sauté guanciale in a large skillet over medium heat until crispy. Turn off the heat.\n3. In a bowl, whisk eggs, egg yolks, cheese, and a generous amount of black pepper.\n4. Add the drained pasta to the skillet with the pork. Pour in the egg mixture and ¼ cup of reserved pasta water.\n5. Toss vigorously off the heat so the eggs create a creamy sauce without scrambling. Add more water as needed for silkiness.",
            cookingTime = "25 mins"
        ),
        Recipe(
            id = 1006,
            title = "Classic Caesar Salad",
            description = "Fresh romaine lettuce with Caesar dressing and croutons.",
            ingredients = listOf("2 heads Romaine lettuce", "½ cup Parmesan cheese (shredded)", "1 cup croutons", "1 egg yolk", "2 anchovy fillets (minced)", "1 clove garlic (minced)", "1 tsp Dijon mustard", "1 tbsp lemon juice", "½ cup extra virgin olive oil"),
            instructions = "1. Make Dressing: Whisk the egg yolk, anchovies, garlic, mustard, and lemon juice. Slowly drizzle in the olive oil while whisking constantly until emulsified. Stir in a little Parmesan.\n2. Prep Lettuce: Rinse and dry Romaine. Tear into bite-sized pieces.\n3. Assemble: Toss lettuce with the dressing until evenly coated. Add croutons and remaining Parmesan.",
            cookingTime = "15 mins"
        ),
        Recipe(
            id = 1007,
            title = "Fluffy Pancakes",
            description = "Golden brown pancakes served with maple syrup.",
            ingredients = listOf("1 ½ cups all-purpose flour", "3 ½ tsp baking powder", "1 tbsp white sugar", "1 tsp salt", "1 ¼ cups milk", "1 egg", "3 tbsp butter (melted)", "Maple syrup"),
            instructions = "1. Mix Dry: Whisk flour, baking powder, sugar, and salt in a large bowl.\n2. Mix Wet: Make a well in the center and pour in milk, egg, and melted butter. Mix until just combined (a few lumps are okay).\n3. Cook: Heat a lightly oiled griddle over medium-high heat. Pour ¼ cup of batter per pancake.\n4. Flip: Cook until bubbles form on the surface and the edges look dry. Flip and cook until golden brown on both sides.",
            cookingTime = "20 mins"
        ),
        Recipe(
            id = 1008,
            title = "Beef and Broccoli Stir Fry",
            description = "Quick and healthy stir fry with tender beef and broccoli.",
            ingredients = listOf("1 lb flank steak (thinly sliced)", "1 lb broccoli florets", "2 tbsp soy sauce", "1 tbsp oyster sauce", "1 tsp ginger (minced)", "2 garlic cloves (minced)", "1 tbsp cornstarch", "¼ cup water", "Vegetable oil", "Cooked white rice"),
            instructions = "1. Marinate: Toss beef with 1 tbsp soy sauce and 1 tsp cornstarch. Let sit for 10 minutes.\n2. Steam Broccoli: Stir-fry broccoli with 2 tbsp water in a hot wok for 2 minutes until bright green. Remove.\n3. Sear Beef: Heat oil in the wok. Sear beef in batches until browned.\n4. Sauce: Return all beef and broccoli to the pan. Stir in remaining soy sauce, oyster sauce, ginger, garlic, and a cornstarch slurry (1 tsp cornstarch mixed with water). Cook until the sauce thickens and coats everything.",
            cookingTime = "25 mins"
        ),
        Recipe(
            id = 1009,
            title = "Margherita Pizza",
            description = "Simple pizza with fresh mozzarella, basil, and tomato sauce.",
            ingredients = listOf("2 ½ cups all-purpose flour", "2 ¼ tsp active dry yeast", "1 tsp sugar", "1 cup warm water", "2 tbsp olive oil", "1 tsp salt", "1 can tomato sauce", "1 tsp dried oregano", "1 clove garlic (minced)", "8 oz fresh mozzarella", "Fresh basil leaves"),
            instructions = "1. Rise Dough: Mix yeast, sugar, and water. Let sit 5 minutes until foamy. Stir in flour, oil, and salt. Knead for 5 minutes. Let rise in a warm place for 45 minutes.\n2. Prep Sauce: Mix tomato sauce, oregano, and minced garlic in a small bowl.\n3. Shape & Bake: Stretch dough onto a greased pan. Spread sauce, add cheese and toppings. Bake at 475°F (245°C) for 12–15 minutes until the crust is golden.",
            cookingTime = "60 mins"
        ),
        Recipe(
            id = 1010,
            title = "Fudgy Chocolate Brownies",
            description = "Rich and decadent chocolate brownies.",
            ingredients = listOf("½ cup butter (melted)", "1 cup white sugar", "2 eggs", "1 tsp vanilla extract", "⅓ cup unsweetened cocoa powder", "½ cup all-purpose flour", "¼ tsp salt", "¼ tsp baking powder"),
            instructions = "1. Prep: Preheat oven to 350°F (175°C). Grease an 8x8 inch baking pan.\n2. Mix: Stir melted butter and sugar together. Add eggs and vanilla; whisk well.\n3. Dry Ingredients: Sift in cocoa, flour, salt, and baking powder. Stir until just combined (do not overmix).\n4. Bake: Spread batter into the pan. Bake for 20–25 minutes. A toothpick should come out with a few moist crumbs. Let cool completely before cutting.",
            cookingTime = "40 mins"
        )
    )
}
