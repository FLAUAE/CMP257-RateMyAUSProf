fetch('api/departments')
    .then(function(res) {
        if (!res.ok) throw new Error('Server error');
        return res.json();
    })
    .then(function(departments) {
        var container = document.getElementById('dept-cards-container');
        container.innerHTML = departments.map(function(dept) {
            return '<div class="col-lg-3 col-md-6">' +
                '<a href="department.html?dept=' + dept.short_id + '" style="text-decoration:none;color:inherit;">' +
                '<div class="department-card">' +
                '<img src="' + dept.image_path + '" alt="' + dept.short_id + '" class="img-fluid">' +
                '<h3>' + dept.short_id + '</h3>' +
                '</div>' +
                '</a>' +
                '</div>';
        }).join('');
    })
    .catch(function() {
        var container = document.getElementById('dept-cards-container');
        container.innerHTML = '<div class="col-12 text-center py-4">Could not load departments. Is the server running?</div>';
    });
