import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPostCategory, defaultValue } from 'app/shared/model/post-category.model';

export const ACTION_TYPES = {
  FETCH_POSTCATEGORY_LIST: 'postCategory/FETCH_POSTCATEGORY_LIST',
  FETCH_POSTCATEGORY: 'postCategory/FETCH_POSTCATEGORY',
  CREATE_POSTCATEGORY: 'postCategory/CREATE_POSTCATEGORY',
  UPDATE_POSTCATEGORY: 'postCategory/UPDATE_POSTCATEGORY',
  DELETE_POSTCATEGORY: 'postCategory/DELETE_POSTCATEGORY',
  RESET: 'postCategory/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPostCategory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PostCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: PostCategoryState = initialState, action): PostCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_POSTCATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_POSTCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_POSTCATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_POSTCATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_POSTCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_POSTCATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_POSTCATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_POSTCATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_POSTCATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_POSTCATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_POSTCATEGORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_POSTCATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_POSTCATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_POSTCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_POSTCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/post-categories';

// Actions

export const getEntities: ICrudGetAllAction<IPostCategory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_POSTCATEGORY_LIST,
  payload: axios.get<IPostCategory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPostCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_POSTCATEGORY,
    payload: axios.get<IPostCategory>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPostCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_POSTCATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPostCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_POSTCATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPostCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_POSTCATEGORY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
