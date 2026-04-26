(function () {
    const endpointCatalog = {
        OWNER_USER_AUTHOR: {
            title: "User and Author Management",
            description: "Maintain users, roles, authors, and book-author relationships.",
            endpoints: [
                { label: "Current user", url: "/api/v1/auth/me" },
                { label: "Authors", url: "/api/v1/authors" },
                { label: "Roles", url: "/api/v1/roles" }
            ]
        },
        OWNER_BOOK: {
            title: "Book Management",
            description: "Manage books, publishers, categories, and states.",
            endpoints: [
                { label: "Books", url: "/api/v1/books" },
                { label: "Categories", url: "/api/v1/categories" },
                { label: "Publishers", url: "/api/v1/publishers" }
            ]
        },
        OWNER_INVENTORY: {
            title: "Inventory Management",
            description: "Control inventory stock and condition records.",
            endpoints: [
                { label: "Inventory", url: "/api/v1/inventory" },
                { label: "Book Conditions", url: "/api/v1/book-conditions" }
            ]
        },
        OWNER_ORDER: {
            title: "Order Management",
            description: "Manage shopping carts and purchase logs.",
            endpoints: [
                { label: "Shopping Cart", url: "/cart/get" },
                { label: "Purchases", url: "/purchase/get" }
            ]
        },
        OWNER_REVIEW: {
            title: "Review Management",
            description: "Reviewers and book review endpoints.",
            endpoints: [
                { label: "Reviewers", url: "/api/reviewers" },
                { label: "Reviews", url: "/api/reviews" }
            ]
        }
    };

    const params = new URLSearchParams(window.location.search);
    const statusBanner = document.getElementById("status-banner");

    if (statusBanner) {
        if (params.has("error")) {
            statusBanner.hidden = false;
            statusBanner.className = "status-banner error";
            statusBanner.textContent = "Authentication failed. Check the owner credentials and try again.";
        } else if (params.has("logout")) {
            statusBanner.hidden = false;
            statusBanner.className = "status-banner success";
            statusBanner.textContent = "You have been signed out.";
        }
    }

    const moduleGrid = document.getElementById("module-grid");
    const endpointSelect = document.getElementById("endpoint-select");
    const runButton = document.getElementById("run-endpoint");
    const output = document.getElementById("api-output");

    if (!moduleGrid || !endpointSelect || !runButton || !output) {
        return;
    }

    fetch("/api/v1/auth/me", {
        headers: {
            "Accept": "application/json"
        }
    })
        .then(async (response) => {
            if (!response.ok) {
                throw new Error("Unable to load session details.");
            }
            return response.json();
        })
        .then((payload) => {
            const data = payload.data;
            document.getElementById("display-name").textContent = `${data.firstName} ${data.lastName}`.trim();
            document.getElementById("display-role").textContent = `${data.username} | ${data.role || "No role assigned"}`;

            const modules = Array.isArray(data.modules) ? data.modules : [];
            const options = [];

            modules.forEach((authority) => {
                const module = endpointCatalog[authority];
                if (!module) {
                    return;
                }

                const card = document.createElement("article");
                card.className = "module-card";
                card.innerHTML = `
                    <span class="badge">Authorized</span>
                    <h3>${module.title}</h3>
                    <p class="muted">${module.description}</p>
                    <ul class="endpoint-list">${module.endpoints.map((endpoint) => `<li>${endpoint.url}</li>`).join("")}</ul>
                `;
                moduleGrid.appendChild(card);

                module.endpoints.forEach((endpoint) => {
                    options.push(endpoint);
                });
            });

            endpointSelect.innerHTML = "";
            options.forEach((endpoint) => {
                const option = document.createElement("option");
                option.value = endpoint.url;
                option.textContent = `${endpoint.label} (${endpoint.url})`;
                endpointSelect.appendChild(option);
            });

            if (!options.length) {
                output.textContent = "This account has no assigned owner modules.";
            }
        })
        .catch((error) => {
            output.textContent = error.message;
        });

    runButton.addEventListener("click", () => {
        const url = endpointSelect.value;
        if (!url) {
            output.textContent = "No endpoint is available for this account.";
            return;
        }

        output.textContent = "Loading...";

        fetch(url, {
            headers: {
                "Accept": "application/json"
            }
        })
            .then(async (response) => {
                const text = await response.text();
                let body;

                try {
                    body = JSON.parse(text);
                } catch (error) {
                    body = text;
                }

                output.textContent = JSON.stringify(
                        {
                            status: response.status,
                            ok: response.ok,
                            body: body
                        },
                        null,
                        2
                );
            })
            .catch((error) => {
                output.textContent = error.message;
            });
    });
})();
