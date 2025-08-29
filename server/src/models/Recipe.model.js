const mongoose = require('mongoose');

const RecipeSchema = new mongoose.Schema({
    // 레시피가 적용될 메뉴 ID
    menuId: { type: mongoose.Schema.Types.ObjectId, ref: 'Menu', required: true, unique: true },
    // 편의를 위해 가게 ID도 저장
    storeId: { type: mongoose.Schema.Types.ObjectId, ref: 'Store', required: true },
    ingredients: [{
        ingredientId: { type: mongoose.Schema.Types.ObjectId, ref: 'Ingredient', required: true },
        quantity: { type: Number, required: true }, // 메뉴 1개당 소모되는 양
    }],
}, { timestamps: true });

module.exports = mongoose.model('Recipe', RecipeSchema);