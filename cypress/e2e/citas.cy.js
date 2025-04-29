describe('Pruebas de Citas API', () => {

    const apiUrl = 'http://localhost:8080/api/citas';
  
    it('Debería listar todas las citas', () => {
      cy.request('GET', apiUrl).then((response) => {
        expect(response.status).to.eq(200);
        expect(response.body).to.be.an('array');
      });
    });
  
    it('Debería obtener una cita por ID existente', () => {
      const citaIdExistente = 1; 
  
      cy.request({
        method: 'GET',
        url: `${apiUrl}/${citaIdExistente}`,
        failOnStatusCode: false
      }).then((response) => {
        expect(response.status).to.eq(200);
        if (response.body) {
          expect(response.body).to.have.property('id', citaIdExistente);
        }
      });
    });
  
    it('Debería eliminar una cita existente', () => {
      const idParaEliminar = 1;
  
      cy.request({
        method: 'DELETE',
        url: `${apiUrl}/${idParaEliminar}`,
        failOnStatusCode: false
      }).then((response) => {
        expect(response.status).to.eq(200);
      });
    });
  
    it('Debería devolver body vacío cuando la cita no existe', () => {
      const idNoExistente = 9999;
  
      cy.request({
        method: 'GET',
        url: `${apiUrl}/${idNoExistente}`,
        failOnStatusCode: false
      }).then((response) => {
        expect(response.status).to.eq(200);
        expect(response.body).to.be.empty;
      });
    });
  
    it('Debería crear una nueva cita', () => {
      const nuevaCita = {
        fechaHora: '2025-05-01T10:00',
        servicio: 'Corte de cabello',
        usuario: { id: 1 }
      };
  
      cy.request({
        method: 'POST',
        url: apiUrl,
        body: nuevaCita,
        failOnStatusCode: false
      }).then((response) => {
        expect(response.status).to.eq(200);
        expect(response.body).to.have.property('id');
        expect(response.body).to.have.property('fechaHora').and.include('2025-05-01T10:00');
      });
    });
  
  });
  