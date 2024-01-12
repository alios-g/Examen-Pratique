package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class ProduitServiceTest {

    private ProduitService produitService;

    @BeforeEach
    void setUp() {
        produitService = new ProduitService();
    }

    @Test
    void createProduit_shouldAddProduct() {
        Produit produit = new Produit(null, "Laptop", 999.99, 5);
        produitService.createProduit(produit);

        assertNotNull(produit.getId());
        Produit retrievedProduit = produitService.readProduit(produit.getId());
        assertNotNull(retrievedProduit);
        assertEquals(produit.getNom(), retrievedProduit.getNom());
        assertEquals(produit.getPrix(), retrievedProduit.getPrix());
        assertEquals(produit.getQuantite(), retrievedProduit.getQuantite());
    }

    @Test
    void createProduit_duplicateId_shouldThrowException() {
        Produit produit1 = new Produit(1L, "Laptop", 999.99, 5);
        produitService.createProduit(produit1);

        final Produit produit2 = new Produit(1L, "Desktop", 799.99, 3);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                produitService.createProduit(produit2);
            }
        });
    }

    @Test
    void createProduit_duplicateName_shouldThrowException() {
        Produit produit1 = new Produit(null, "Laptop", 999.99, 5);
        produitService.createProduit(produit1);

        final Produit produit2 = new Produit(null, "Laptop", 799.99, 3);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                produitService.createProduit(produit2);
            }
        });
    }

    @Test
    void createProduit_negativePriceOrQuantity_shouldThrowException() {
        final Produit produit = new Produit(null, "Laptop", -999.99, -5);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                produitService.createProduit(produit);
            }
        });
    }

    // Similar tests for readProduit, updateProduit, and deleteProduit methods...

    @Test
    void deleteProduit_existingId_shouldRemoveProduct() {
        Produit produit = new Produit(null, "Tablet", 299.99, 10);
        produitService.createProduit(produit);

        produitService.deleteProduit(produit.getId());
        assertNull(produitService.readProduit(produit.getId()));
    }

    @Test
    void deleteProduit_nonExistingId_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                produitService.deleteProduit(100L);
            }
        });
    }
}
