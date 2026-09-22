const ingredientInput = document.getElementById("ingredientInput");
const addButton = document.getElementById("addButton");
const ingredientList = document.getElementById("ingredientList");

function addIngredient() {
    const ingredient = ingredientInput.value.trim();

    if (ingredient === "") {
        return;
    }

    const tag = document.createElement("div");
    tag.classList.add("ingredient-tag");
    tag.textContent = ingredient;

    ingredientList.appendChild(tag);

    ingredientInput.value = "";
    ingredientInput.focus();
}

addButton.addEventListener("click", addIngredient);

ingredientInput.addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
        addIngredient();
    }
});

const cookButton = document.getElementById("cookButton");
const recipeSection = document.getElementById("recipeSection");

cookButton.addEventListener("click", function() {
    if (ingredientList.children.length === 0) {
        recipeSection.innerHTML = `
            <h2>Add some ingredients first! 👀</h2>
            <p>Tell us what's in your kitchen.</p>
        `;
        return;
    }

    recipeSection.innerHTML = `
        <div class="recipe-card">
            <div class="recipe-emoji">🍳</div>
            <h2>Egg Fried Rice</h2>
            <p class="recipe-description">
                A quick and tasty meal using ingredients from your kitchen.
            </p>

            <div class="recipe-info">
                <span>⭐ Easy</span>
                <span>⏱ 20 min</span>
            </div>

            <button class="view-recipe-button">
                VIEW RECIPE →
            </button>
        </div>
    `;
});