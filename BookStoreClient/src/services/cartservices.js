import { axios_obj } from '../http-common.js';

class CartService {

    getCartItems(userId) {
        return axios_obj.get(`/cart/${userId}`)
    }

    addToCart(data) {
        return axios_obj.post("/cart/update", data)
    }

    removeFromCart(data) {
        return axios_obj.delete("/cart/remove", {data})
    }
    
}

export default new CartService();