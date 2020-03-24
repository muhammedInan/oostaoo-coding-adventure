/* global Payment */
'use strict';

/**
 * Payment.js service
 *
 * @description: A set of functions similar to controller's actions to avoid code duplication.
 */

// Public dependencies.
const _ = require('lodash');
const STRIPE_API_KEY = "sk_test_SHGN7PIdottD4WBLCcdSfbwA00kPGubvOC";
const stripe = require('stripe')(STRIPE_API_KEY);

// Strapi utilities.
const utils = require('strapi-hook-bookshelf/lib/utils/');

module.exports = {

  /**
   * Promise to fetch all payments.
   *
   * @return {Promise}
   */

  fetchAll: (params) => {
    // Convert `params` object to filters compatible with Bookshelf.
    const filters = strapi.utils.models.convertParams('payment', params);
    // Select field to populate.
    const populate = Payment.associations
      .filter(ast => ast.autoPopulate !== false)
      .map(ast => ast.alias);

    return Payment.query(function(qb) {
      _.forEach(filters.where, (where, key) => {
        if (_.isArray(where.value) && where.symbol !== 'IN' && where.symbol !== 'NOT IN') {
          for (const value in where.value) {
            qb[value ? 'where' : 'orWhere'](key, where.symbol, where.value[value])
          }
        } else {
          qb.where(key, where.symbol, where.value);
        }
      });

      if (filters.sort) {
        qb.orderBy(filters.sort.key, filters.sort.order);
      }

      qb.offset(filters.start);
      qb.limit(filters.limit);
    }).fetchAll({
      withRelated: filters.populate || populate
    });
  },

  /**
   * Promise to fetch a/an payment.
   *
   * @return {Promise}
   */

  fetch: (params) => {
    // Select field to populate.
    const populate = Payment.associations
      .filter(ast => ast.autoPopulate !== false)
      .map(ast => ast.alias);

    return Payment.forge(_.pick(params, 'id')).fetch({
      withRelated: populate
    });
  },

  /**
   * Promise to count a/an payment.
   *
   * @return {Promise}
   */

  count: (params) => {
    // Convert `params` object to filters compatible with Bookshelf.
    const filters = strapi.utils.models.convertParams('payment', params);

    return Payment.query(function(qb) {
      _.forEach(filters.where, (where, key) => {
        if (_.isArray(where.value)) {
          for (const value in where.value) {
            qb[value ? 'where' : 'orWhere'](key, where.symbol, where.value[value]);
          }
        } else {
          qb.where(key, where.symbol, where.value);
        }
      });
    }).count();
  },

  /**
   * Promise to add a/an payment.
   *
  //  * @return {Promise}
   */

  add: async (values) => {
    // Extract values related to relational data.
    console.log('SERVICE ADD : values : ', values);

    const relations = _.pick(values, Payment.associations.map(ast => ast.alias));
    // console.log('relations : ', relations);
    const data = _.omit(values, Payment.associations.map(ast => ast.alias));
    // console.log('data : ', data);
    // Create entry with no-relational data.
    const entry = await Payment.forge(data).save();

    // Create relational data and return the entry.
    return Payment.updateRelations({ id: entry.id , values: relations });
  },

  /**
   * Promise to edit a/an payment.
   *
   * @return {Promise}
   */
  refund: async (paymentId) => {
    console.log('SERVICE REFUND : paymentId : ', paymentId);
      let refund;
      const paymentType = paymentId.substring(0, 2);
      if (paymentType == 'ch'){
         refund = await stripe.refunds.create({
          charge: paymentId
        });
      }else if (paymentType == 'su'){
        refund = await stripe.subscriptions.del(paymentId);
      }


    return refund;
  },

  edit: async (params, values) => {
    // Extract values related to relational data.
    const relations = _.pick(values, Payment.associations.map(ast => ast.alias));
    const data = _.omit(values, Payment.associations.map(ast => ast.alias));

    // Create entry with no-relational data.
    const entry = await Payment.forge(params).save(data);

    // Create relational data and return the entry.
    return Payment.updateRelations(Object.assign(params, { values: relations }));
  },

  /**
   * Promise to remove a/an payment.
   *
   * @return {Promise}
   */

  remove: async (params) => {
    params.values = {};
    Payment.associations.map(association => {
      switch (association.nature) {
        case 'oneWay':
        case 'oneToOne':
        case 'manyToOne':
        case 'oneToManyMorph':
          params.values[association.alias] = null;
          break;
        case 'oneToMany':
        case 'manyToMany':
        case 'manyToManyMorph':
          params.values[association.alias] = [];
          break;
        default:
      }
    });

    await Payment.updateRelations(params);

    return Payment.forge(params).destroy();
  },

  /**
   * Promise to search a/an payment.
   *
   * @return {Promise}
   */

  search: async (params) => {
    // Convert `params` object to filters compatible with Bookshelf.
    const filters = strapi.utils.models.convertParams('payment', params);
    // Select field to populate.
    const populate = Payment.associations
      .filter(ast => ast.autoPopulate !== false)
      .map(ast => ast.alias);

    const associations = Payment.associations.map(x => x.alias);
    const searchText = Object.keys(Payment._attributes)
      .filter(attribute => attribute !== Payment.primaryKey && !associations.includes(attribute))
      .filter(attribute => ['string', 'text'].includes(Payment._attributes[attribute].type));

    const searchNoText = Object.keys(Payment._attributes)
      .filter(attribute => attribute !== Payment.primaryKey && !associations.includes(attribute))
      .filter(attribute => !['string', 'text', 'boolean', 'integer', 'decimal', 'float'].includes(Payment._attributes[attribute].type));

    const searchInt = Object.keys(Payment._attributes)
      .filter(attribute => attribute !== Payment.primaryKey && !associations.includes(attribute))
      .filter(attribute => ['integer', 'decimal', 'float'].includes(Payment._attributes[attribute].type));

    const searchBool = Object.keys(Payment._attributes)
      .filter(attribute => attribute !== Payment.primaryKey && !associations.includes(attribute))
      .filter(attribute => ['boolean'].includes(Payment._attributes[attribute].type));

    const query = (params._q || '').replace(/[^a-zA-Z0-9.-\s]+/g, '');

    return Payment.query(qb => {
      // Search in columns which are not text value.
      searchNoText.forEach(attribute => {
        qb.orWhereRaw(`LOWER(${attribute}) LIKE '%${_.toLower(query)}%'`);
      });

      if (!_.isNaN(_.toNumber(query))) {
        searchInt.forEach(attribute => {
          qb.orWhereRaw(`${attribute} = ${_.toNumber(query)}`);
        });
      }

      if (query === 'true' || query === 'false') {
        searchBool.forEach(attribute => {
          qb.orWhereRaw(`${attribute} = ${_.toNumber(query === 'true')}`);
        });
      }

      // Search in columns with text using index.
      switch (Payment.client) {
        case 'mysql':
          qb.orWhereRaw(`MATCH(${searchText.join(',')}) AGAINST(? IN BOOLEAN MODE)`, `*${query}*`);
          break;
        case 'pg': {
          const searchQuery = searchText.map(attribute =>
            _.toLower(attribute) === attribute
              ? `to_tsvector(${attribute})`
              : `to_tsvector('${attribute}')`
          );

          qb.orWhereRaw(`${searchQuery.join(' || ')} @@ to_tsquery(?)`, query);
          break;
        }
      }

      if (filters.sort) {
        qb.orderBy(filters.sort.key, filters.sort.order);
      }

      if (filters.skip) {
        qb.offset(_.toNumber(filters.skip));
      }

      if (filters.limit) {
        qb.limit(_.toNumber(filters.limit));
      }
    }).fetchAll({
      withRelated: populate
    });
  },

  charge: async (body) => {
    let payment;
    const customer = await stripe.customers.create({
      email: body.email,
      source: body.token.id,
    })
    try{
        payment = await stripe.charges.create({
         amount: body.offer.price*100,
         currency: 'EUR',
         customer: customer.id
     });
    return payment;

    }catch(error){
     console.log('error : ', error);
     return error;
   }
    // return await stripe.charges.create({
    //   source: body.token.id,
    //   amount: body.amount,
    //   currency: 'eur',
    // })
  },

  subscribe: async (body) => {
    let payment;
      const customer = await stripe.customers.create({
        email: body.email,
        source: body.token.id,
      })

         try {
           payment = await stripe.subscriptions.create({
             customer: customer.id,
             items: [{
             plan: body.offer.plan
             }]
          });

          return payment;
        }catch(error){
          console.log('error : ', error);
          return error;
        }
  }
};
  // subscribe: async (body) => {
  //   const session = stripe.checkout.sessions.create({})
  // }