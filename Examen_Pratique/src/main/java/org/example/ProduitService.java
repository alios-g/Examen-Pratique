package org.example;

import java.util.HashMap;
import java.util.Map;

public class ProduitService {

    private final Map<Long, Produit> produits = new HashMap<>();
    private long nextId = 1;

    // Create
    public void createProduit(Produit produit) {
        validateProduit(produit);
        verifyUnicity(produit);

        produit.setId(nextId++);
        produits.put(produit.getId(), produit);
    }

    // Read
    public Produit readProduit(Long id) {
        return produits.get(id);
    }

    // Update
    public void updateProduit(Produit updatedProduit) {
        validateProduit(updatedProduit);

        Produit existingProduit = produits.get(updatedProduit.getId());
        if (existingProduit != null) {
            verifyUnicity(updatedProduit);
            produits.put(updatedProduit.getId(), updatedProduit);
        } else {
            throw new IllegalArgumentException("Product with ID " + updatedProduit.getId() + " does not exist.");
        }
    }

    // Delete
    public void deleteProduit(Long id) {
        if (produits.containsKey(id)) {
            produits.remove(id);
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " does not exist.");
        }
    }

    // Validation of Data
    private void validateProduit(Produit produit) {
        if (produit.getPrix() < 0 || produit.getQuantite() < 0) {
            throw new IllegalArgumentException("Price and quantity must be positive values.");
        }
    }

    // Unicity Check
    private void verifyUnicity(Produit produit) {
        for (Produit existingProduit : produits.values()) {
            if (existingProduit.getId() != null && existingProduit.getId().equals(produit.getId())) {
                throw new IllegalArgumentException("Product with ID " + produit.getId() + " already exists.");
            }
            if (existingProduit.getNom().equals(produit.getNom())) {
                throw new IllegalArgumentException("Product with name " + produit.getNom() + " already exists.");
            }
        }
    }
}
