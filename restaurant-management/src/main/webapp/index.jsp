<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurant Management</title>
    <script>
        async function fetchMenuItems() {
            const response = await fetch('/api/menuitems');
            const menuItems = await response.json();
            const list = document.getElementById('menu-list');
            list.innerHTML = '';
            menuItems.forEach(item => {
                const li = document.createElement('li');
                li.textContent = `${item.name} - $${item.price.toFixed(2)}: ${item.description}`;
                list.appendChild(li);
            });
        }

        async function addMenuItem() {
            const name = document.getElementById('name').value;
            const description = document.getElementById('description').value;
            const price = parseFloat(document.getElementById('price').value);
            const response = await fetch('/api/menuitems', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ name, description, price })
            });
            if (response.ok) {
                fetchMenuItems();
                document.getElementById('name').value = '';
                document.getElementById('description').value = '';
                document.getElementById('price').value = '';
            } else {
                alert('Failed to add menu item');
            }
        }

        window.onload = fetchMenuItems;
    </script>
</head>
<body>
<h1>Restaurant Management System</h1>

<h2>Menu Management</h2>
<ul id="menu-list"></ul>

<h3>Add Menu Item</h3>
<label for="name">Name:</label>
<input type="text" id="name" name="name" required><br>
<label for="description">Description:</label>
<input type="text" id="description" name="description" required><br>
<label for="price">Price:</label>
<input type="number" id="price" name="price" step="0.01" required><br>
<button onclick="addMenuItem()">Add</button>

<hr>

<h2>Order Management</h2>
<ul id="order-list"></ul>

<h3>Add Order</h3>
<label for="tableId">Table ID:</label>
<input type="number" id="tableId" name="tableId" required><br>
<label for="menuItemIds">Menu Item IDs (comma separated):</label>
<input type="text" id="menuItemIds" name="menuItemIds" required><br>
<label for="status">Status:</label>
<input type="text" id="status" name="status" required><br>
<button onclick="addOrder()">Add</button>

<hr>

<h2>Table Management</h2>
<ul id="table-list"></ul>

<h3>Add Table</h3>
<label for="tableName">Name:</label>
<input type="text" id="tableName" name="tableName" required><br>
<label for="capacity">Capacity:</label>
<input type="number" id="capacity" name="capacity" required><br>
<label for="available">Available:</label>
<input type="checkbox" id="available" name="available"><br>
<button onclick="addTable()">Add</button>

<hr>

<h2>Staff Management</h2>
<ul id="staff-list"></ul>

<h3>Add Staff</h3>
<label for="staffName">Name:</label>
<input type="text" id="staffName" name="staffName" required><br>
<label for="role">Role:</label>
<input type="text" id="role" name="role" required><br>
<label for="contact">Contact:</label>
<input type="text" id="contact" name="contact" required><br>
<button onclick="addStaff()">Add</button>

<script>
    async function fetchMenuItems() {
        const response = await fetch('/api/menuitems');
        const menuItems = await response.json();
        const list = document.getElementById('menu-list');
        list.innerHTML = '';
        menuItems.forEach(item => {
            const li = document.createElement('li');
            li.textContent = `${item.name} - $${item.price.toFixed(2)}: ${item.description}`;
            list.appendChild(li);
        });
    }

    async function addMenuItem() {
        const name = document.getElementById('name').value;
        const description = document.getElementById('description').value;
        const price = parseFloat(document.getElementById('price').value);
        const response = await fetch('/api/menuitems', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, description, price })
        });
        if (response.ok) {
            fetchMenuItems();
            document.getElementById('name').value = '';
            document.getElementById('description').value = '';
            document.getElementById('price').value = '';
        } else {
            alert('Failed to add menu item');
        }
    }

    async function fetchOrders() {
        const response = await fetch('/api/orders');
        const orders = await response.json();
        const list = document.getElementById('order-list');
        list.innerHTML = '';
        orders.forEach(order => {
            const li = document.createElement('li');
            li.textContent = `Order #${order.id} - Table: ${order.tableId} - Status: ${order.status} - Items: ${order.menuItemIds.join(', ')}`;
            list.appendChild(li);
        });
    }

    async function addOrder() {
        const tableId = parseInt(document.getElementById('tableId').value);
        const menuItemIds = document.getElementById('menuItemIds').value.split(',').map(id => parseInt(id.trim()));
        const status = document.getElementById('status').value;
        const response = await fetch('/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ tableId, menuItemIds, status })
        });
        if (response.ok) {
            fetchOrders();
            document.getElementById('tableId').value = '';
            document.getElementById('menuItemIds').value = '';
            document.getElementById('status').value = '';
        } else {
            alert('Failed to add order');
        }
    }

    async function fetchTables() {
        const response = await fetch('/api/tables');
        const tables = await response.json();
        const list = document.getElementById('table-list');
        list.innerHTML = '';
        tables.forEach(table => {
            const li = document.createElement('li');
            li.textContent = `Table #${table.id} - ${table.name} - Capacity: ${table.capacity} - Available: ${table.available}`;
            list.appendChild(li);
        });
    }

    async function addTable() {
        const name = document.getElementById('tableName').value;
        const capacity = parseInt(document.getElementById('capacity').value);
        const available = document.getElementById('available').checked;
        const response = await fetch('/api/tables', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, capacity, available })
        });
        if (response.ok) {
            fetchTables();
            document.getElementById('tableName').value = '';
            document.getElementById('capacity').value = '';
            document.getElementById('available').checked = false;
        } else {
            alert('Failed to add table');
        }
    }

    async function fetchStaff() {
        const response = await fetch('/api/staff');
        const staffList = await response.json();
        const list = document.getElementById('staff-list');
        list.innerHTML = '';
        staffList.forEach(staff => {
            const li = document.createElement('li');
            li.textContent = `${staff.name} - Role: ${staff.role} - Contact: ${staff.contact}`;
            list.appendChild(li);
        });
    }

    async function addStaff() {
        const name = document.getElementById('staffName').value;
        const role = document.getElementById('role').value;
        const contact = document.getElementById('contact').value;
        const response = await fetch('/api/staff', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, role, contact })
        });
        if (response.ok) {
            fetchStaff();
            document.getElementById('staffName').value = '';
            document.getElementById('role').value = '';
            document.getElementById('contact').value = '';
        } else {
            alert('Failed to add staff');
        }
    }

    window.onload = () => {
        fetchMenuItems();
        fetchOrders();
        fetchTables();
        fetchStaff();
    };
</script>
</body>
</html>
